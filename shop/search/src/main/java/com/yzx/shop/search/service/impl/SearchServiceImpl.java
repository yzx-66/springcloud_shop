package com.yzx.shop.search.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yzx.shop.commen.entity.PageResult;
import com.yzx.shop.item.entity.*;
import com.yzx.shop.search.client.BrandClient;
import com.yzx.shop.search.client.CategoryClient;
import com.yzx.shop.search.client.GoodsClient;
import com.yzx.shop.search.client.SpecificationClient;
import com.yzx.shop.search.entity.Goods;
import com.yzx.shop.search.entity.SearchRequest;
import com.yzx.shop.search.entity.SearchResult;
import com.yzx.shop.search.repository.GoodsRepository;
import com.yzx.shop.search.service.SearchService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private SpecificationClient specificationClient;

    @Autowired
    private GoodsRepository goodsRepository;

    @Override
    public Goods buildGoods(Spu spu) throws IOException {
        Goods goods=new Goods();
        goods.setId(spu.getId());
        goods.setSubTitle(spu.getSubTitle());
        goods.setBrandId(spu.getBrandId());
        goods.setCid1(spu.getCid1());
        goods.setCid2(spu.getCid2());
        goods.setCid3(spu.getCid3());
        goods.setCreateTime(new Date());

        //all
        List<String> categoryNameList=categoryClient.queryNamesByIds(Arrays.asList(spu.getCid1(),spu.getCid2(),spu.getCid3()));
        String categoryNames= StringUtils.join(categoryNameList," ");
        String all=categoryNames+" "+spu.getTitle()+" "+brandClient.queryBrandById(spu.getBrandId()).getName();
        goods.setAll(all);

        //prices、skus
        List<Sku> skuList=goodsClient.querySkuListBySpuId(spu.getId());
        List<Long> pricesList=new ArrayList<>();
        List<Map<String,Object>> skuMapList=new ArrayList<>();
        ObjectMapper objectMapper=new ObjectMapper();

        skuList.forEach(s->{
            pricesList.add(s.getPrice());

            Map<String ,Object> skuParamMap=new HashMap<>();
            skuParamMap.put("id",s.getId());
            skuParamMap.put("image",StringUtils.isNotBlank(s.getImages())?StringUtils.split(s.getImages(),",")[0]:"");
            skuParamMap.put("price",s.getPrice());
            skuParamMap.put("tittle",s.getTitle());
            skuMapList.add(skuParamMap);
        });

        goods.setPrice(pricesList);
        goods.setSkus(objectMapper.writeValueAsString(skuMapList));

        //params
        SpuDetail spuDetail = goodsClient.querySpuDetailBySid(spu.getId());
        Map<String,Object> genericSpecMap = objectMapper.readValue(spuDetail.getGenericSpec(), new TypeReference<Map<String, Object>>() {});
        Map<String,List<Object>> specialSpecMap=objectMapper.readValue(spuDetail.getSpecialSpec(),new TypeReference<Map<String,List<Object>>>(){});
        List<SpecParam> specParams = specificationClient.querySpecParanmByGidOrCid3(null, spu.getCid3());
        Map<String,Object> specsMap=new HashMap<>();

        specParams.forEach(specParam -> {
            if(specParam.getGeneric()){
                String value=genericSpecMap.get(specParam.getId().toString()).toString();
                if(specParam.getNumeric()){
                    value=chooseSegment(value,specParam);
                }
                specsMap.put(specParam.getName(),value);
            }else {
                if(specParam.getNumeric()){
                    List<String> values=new ArrayList<>();
                    specialSpecMap.get(specParam.getId().toString()).forEach(s->{
                        values.add(chooseSegment(s.toString(),specParam));
                    });
                    specsMap.put(specParam.getName(),values);
                }else {
                    specsMap.put(specParam.getName(),specialSpecMap.get(specParam.getId().toString()));
                }
            }
        });

        goods.setSpecs(specsMap);
        return goods;
    }

    @Override
    public SearchResult queryGoods(SearchRequest searchRequest) {
        if(StringUtils.isBlank(searchRequest.getKey())){
            return null;
        }

        NativeSearchQueryBuilder nativeSearchQueryBuilder=new NativeSearchQueryBuilder();
        //nativeSearchQueryBuilder.withQuery(QueryBuilders.matchQuery("all",searchRequest.getKey()).operator(Operator.AND));
        BoolQueryBuilder basicBuilder=getQueryBuilbulider(searchRequest);
        nativeSearchQueryBuilder.withQuery(basicBuilder);
        nativeSearchQueryBuilder.withPageable(PageRequest.of(searchRequest.getPage()-1,searchRequest.getSize()));
        nativeSearchQueryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id", "skus", "subTitle"},null));

        String brandAggName="brands";
        String categoryAggName="categorys";
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms(brandAggName).field("brandId"));
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms(categoryAggName).field("cid3"));

        AggregatedPage<Goods> goods = (AggregatedPage<Goods>) goodsRepository.search(nativeSearchQueryBuilder.build());
        List<Brand> brandAggRes = getBrandAggRes(goods.getAggregation(brandAggName));
        List<Map<String, Object>> categoryAggRes = getCategoryAggRes(goods.getAggregation(categoryAggName));
        List<Map<String,Object>> specAggRes = null;
        if(!CollectionUtils.isEmpty(categoryAggRes) && categoryAggRes.size()==1){
            specAggRes=getSpecAggRes((Long) categoryAggRes.get(0).get("id"),basicBuilder);
        }
        return new SearchResult(goods.getTotalElements(),goods.getTotalPages(),goods.getContent(),brandAggRes,categoryAggRes,specAggRes);
    }

    @Override
    public void createIndex(Long id) throws IOException {
        Spu spu = goodsClient.querySpuById(id);
        Goods goods = buildGoods(spu);
        goodsRepository.save(goods);
    }

    @Override
    public void deleteIndex(Long id) {
        goodsRepository.deleteById(id);
    }

    BoolQueryBuilder getQueryBuilbulider(SearchRequest searchRequest){
        BoolQueryBuilder boolQueryBuilder=new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.matchQuery("all",searchRequest.getKey()).operator(Operator.AND));
        if(searchRequest.getFilter()!=null){
            for (Map.Entry<String, Object> filter : searchRequest.getFilter().entrySet()) {
                String f=filter.getKey();
                if(f.equals("品牌")){
                    f="brandId";
                }else if(f.equals("分类")){
                    f="cid3";
                }else {
                    f="specs."+filter.getKey()+".keyword";
                }
                boolQueryBuilder.filter(QueryBuilders.termQuery(f,filter.getValue()));
            }
        }
        return boolQueryBuilder;
    }

    public List<Brand> getBrandAggRes(Aggregation agg){
        LongTerms longTerms= (LongTerms) agg;
        return longTerms.getBuckets().stream().map(bucket ->
                brandClient.queryBrandById(bucket.getKeyAsNumber().longValue())).collect(Collectors.toList());
    }

    public List<Map<String,Object>> getCategoryAggRes(Aggregation agg){
        LongTerms longTerms= (LongTerms) agg;

        return longTerms.getBuckets().stream().map(bucket -> {
            Map<String,Object> map=new HashMap<>();
            long id=bucket.getKeyAsNumber().longValue();
            List<String> names = categoryClient.queryNamesByIds(Arrays.asList(id));
            map.put("id",id);
            map.put("name", names.get(0));
            return map;
        }).collect(Collectors.toList());
    }

    public List<Map<String,Object>> getSpecAggRes(long cid, QueryBuilder queryBuilder){
        List<SpecParam> specParamList = specificationClient.querySpecParanmByGidOrCid3(null, cid);

        NativeSearchQueryBuilder nativeSearchQueryBuilder=new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(queryBuilder);
        specParamList.forEach(specParam -> {
            if(specParam.getSearching()){
                nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms(specParam.getName()).field("specs."+specParam.getName()+".keyword"));
            }
        });

        nativeSearchQueryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{},null));
        NativeSearchQuery build = nativeSearchQueryBuilder.build();
        AggregatedPage<Goods> goodsPage = (AggregatedPage<Goods>) goodsRepository.search(build);

        Map<String, Aggregation> aggregationMap = goodsPage.getAggregations().asMap();
        List<Map<String ,Object>> specResList=new ArrayList<>();

        for(Map.Entry<String, Aggregation> agg:aggregationMap.entrySet()){
            Map<String,Object> map=new HashMap<>();
            List<Object> options=new ArrayList<>();
            map.put("k",agg.getKey());

            StringTerms aggValue = (StringTerms) agg.getValue();
            List<StringTerms.Bucket> buckets = aggValue.getBuckets();
            buckets.forEach(bucket -> {
                options.add(bucket.getKeyAsString());
            });

            map.put("options",options);
            specResList.add(map);
        }

        return specResList;

    }

    public String chooseSegment(String value,SpecParam specParam){
        String result="其他";
        Double val;
        try{
             val= Double.parseDouble(value);
        }catch (Exception e){
            return result;
        }

        for(String segment:StringUtils.split(specParam.getSegments(),",")){
            Double begin=Double.MIN_VALUE;
            Double end=Double.MAX_VALUE;
            String[] nums=StringUtils.split(segment,"-");

            if(nums.length==2){
                begin=Double.parseDouble(nums[0]);
                end=Double.parseDouble(nums[1]);
            }else {
                if(StringUtils.endsWith(segment,"-")){
                    begin=Double.parseDouble(nums[0]);
                }
                if(StringUtils.startsWith(segment,"-")){
                    end=Double.parseDouble(nums[0]);
                }
            }


            if(val>=begin && val<end){
                if(StringUtils.startsWith(segment,"-")){
                    result=end.toString()+specParam.getUnit()+"以下";
                }else if(StringUtils.endsWith(segment,"-")){
                    result=begin.toString()+specParam.getUnit()+"以上";
                }else {
                    result=segment+specParam.getUnit();
                }
                break;
            }
        }
        return result;
    }
}
