package com.yzx.shop.goodsWeb.service.impl;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.yzx.shop.goodsWeb.client.BrandClient;
import com.yzx.shop.goodsWeb.client.CategoryClient;
import com.yzx.shop.goodsWeb.client.GoodsClient;
import com.yzx.shop.goodsWeb.client.SpecificationClient;
import com.yzx.shop.goodsWeb.service.GoodsService;
import com.yzx.shop.item.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private BrandClient brandClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SpecificationClient specificationClient;


    @Override
    public Map<String, Object> loadData(Long spuId) {
        Map<String,Object> model=new HashMap<>();
        
        Spu spu=goodsClient.querySpuById(spuId);
        SpuDetail spuDetail=goodsClient.querySpuDetailBySid(spuId);

        List<String> cnames = categoryClient.queryNamesByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        List<Long> cids=Arrays.asList(spu.getCid1(),spu.getCid2(),spu.getCid3());
        List<Map<String,Object>> categories=new ArrayList<>();
        for(int i=0;i<3;i++){
            Map<String,Object> category=new HashMap<>();
            category.put("id",cids.get(i));
            category.put("name",cnames.get(i));
            categories.add(category);
        }

        Brand brand=brandClient.queryBrandById(spu.getBrandId());
        List<Sku> skus=goodsClient.querySkuListBySpuId(spu.getId());
        List<SpecGroup> groups=specificationClient.querySpecGroupAndParamsByCid(spu.getCid3());

        List<SpecParam> specParams = specificationClient.querySpecParanmByGidOrCid3(null, spu.getCid3());
        Map<Long,Object> paramMap=new HashMap<>();
        specParams.forEach(specParam -> {
            paramMap.put(specParam.getId(),specParam.getName());
        });

        model.put("spu", spu);
        model.put("spuDetail", spuDetail);
        model.put("categories", categories);
        model.put("brand", brand);
        model.put("skus", skus);
        model.put("groups", groups);
        model.put("paramMap", paramMap);
        return model;
    }
}
