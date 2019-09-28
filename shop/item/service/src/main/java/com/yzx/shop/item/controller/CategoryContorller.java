package com.yzx.shop.item.controller;

import com.yzx.shop.item.entity.Category;
import com.yzx.shop.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("category")
public class CategoryContorller {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("list")
    public ResponseEntity<List<Category>> queryCategoriesByPid(@RequestParam(value = "pid",defaultValue = "0")Long pid){
        if(pid==null||pid<0){
            return ResponseEntity.badRequest().build();
        }

        List<Category> categoryList = categoryService.queryCategoriesByPid(pid);
        if(categoryList.size()<=0){
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoryList);
    }

    @GetMapping
    public ResponseEntity<List<String>> queryNamesByIds(@RequestParam("ids")List<Long> ids){
        List<String> names = categoryService.queryNamesByIdList(ids);
        if (CollectionUtils.isEmpty(names)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(names);
    }

    @GetMapping("all/level")
    public ResponseEntity<List<Category>> queryAllCategorysByCid3(@RequestParam("id")Long id3){
        List<Category> categories=categoryService.queryAllCategorysByCid3(id3);
        if (CollectionUtils.isEmpty(categories)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categories);
    }
}
