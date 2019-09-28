package com.yzx.shop.search.controller;

import com.yzx.shop.commen.entity.PageResult;
import com.yzx.shop.search.entity.Goods;
import com.yzx.shop.search.entity.SearchRequest;
import com.yzx.shop.search.entity.SearchResult;
import com.yzx.shop.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @PostMapping("page")
    public ResponseEntity<SearchResult> queryGoods(@RequestBody SearchRequest searchRequest){
        SearchResult goodsPageResult=searchService.queryGoods(searchRequest);
        if(goodsPageResult==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(goodsPageResult);
    }

    @GetMapping("test")
    public ResponseEntity<Void> test(){
        return ResponseEntity.ok(null);
    }
}
