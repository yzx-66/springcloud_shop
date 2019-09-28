package com.yzx.shop.search;

import com.yzx.shop.commen.entity.PageResult;
import com.yzx.shop.item.bo.SpuBo;
import com.yzx.shop.item.entity.Spu;
import com.yzx.shop.search.client.GoodsClient;
import com.yzx.shop.search.entity.Goods;
import com.yzx.shop.search.repository.GoodsRepository;
import com.yzx.shop.search.service.SearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ESTests {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SearchService searchService;

    @Autowired
    private GoodsRepository goodsRepository;

    @Test
    public void test01(){
       elasticsearchTemplate.createIndex(Goods.class);
       elasticsearchTemplate.putMapping(Goods.class);
    }

    @Test
    public void test02() throws IOException {
        int page=1;
        while (true){
            PageResult<SpuBo> spuBoPageResult = goodsClient.querySpuList(null, null, page++, 3);
            List<Goods> goodsList=new ArrayList<>();

            for(Spu spu:spuBoPageResult.getItems()){
                Goods goods = searchService.buildGoods(spu);
                goodsList.add(goods);
            }
            goodsRepository.saveAll(goodsList);

            if(spuBoPageResult.getItems().size()<3){
                break;
            }
        }
    }
}
