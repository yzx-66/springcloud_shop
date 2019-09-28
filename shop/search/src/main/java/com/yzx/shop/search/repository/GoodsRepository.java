package com.yzx.shop.search.repository;

import com.yzx.shop.search.entity.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface GoodsRepository extends ElasticsearchRepository<Goods,Long> {
}
