package com.yzx.shop.item.controller;

import com.yzx.shop.commen.entity.PageResult;
import com.yzx.shop.item.entity.Brand;
import com.yzx.shop.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryBrandByPage(@RequestParam(value = "key",required = false)String key,
                                                              @RequestParam(value = "page",defaultValue = "0")Integer page,
                                                              @RequestParam(value = "rows",defaultValue = "5")Integer rows,
                                                              @RequestParam(value = "sortBy",required = false)String sortBy,
                                                              @RequestParam(value = "desc",required = false)boolean desc){
        PageResult<Brand> pageResult= brandService.queryBrandByPage(key,page,rows,sortBy,desc);
        if(CollectionUtils.isEmpty(pageResult.getItems())){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(pageResult);
    }

    @PostMapping
    public ResponseEntity<Void> insertBrandAndCategory(Brand brand,@RequestParam(value = "cids") List<Long> cids){
        brandService.insertBrandAndCategory(brand,cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // /brand/cid/76
    @GetMapping("cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandsByCid3(@PathVariable("cid")Long cid3){
        List<Brand> brands=brandService.queryBrandsByCid3(cid3);
        if(CollectionUtils.isEmpty(brands)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brands);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Brand> queryBrandById(@PathVariable("id")Long id){
        Brand brand= brandService.queryBrandById(id);
        return ResponseEntity.ok(brand);
    }
}
