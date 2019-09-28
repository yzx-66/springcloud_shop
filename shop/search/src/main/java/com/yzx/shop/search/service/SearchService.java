package com.yzx.shop.search.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yzx.shop.commen.entity.PageResult;
import com.yzx.shop.item.entity.Spu;
import com.yzx.shop.search.entity.Goods;
import com.yzx.shop.search.entity.SearchRequest;
import com.yzx.shop.search.entity.SearchResult;

import java.io.IOException;

public interface SearchService {

    Goods buildGoods(Spu spu) throws IOException;

    SearchResult queryGoods(SearchRequest searchRequest);

    void createIndex(Long id) throws IOException;

    void deleteIndex(Long id);
}
