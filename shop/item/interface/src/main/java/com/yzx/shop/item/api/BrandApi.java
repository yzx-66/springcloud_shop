package com.yzx.shop.item.api;

import com.yzx.shop.commen.entity.PageResult;
import com.yzx.shop.item.entity.Brand;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("brand")
public interface BrandApi {

    @GetMapping("page")
    public PageResult<Brand> queryBrandByPage(@RequestParam(value = "key",required = false)String key,
                                                              @RequestParam(value = "page",defaultValue = "0")Integer page,
                                                              @RequestParam(value = "rows",defaultValue = "5")Integer rows,
                                                              @RequestParam(value = "sortBy",required = false)String sortBy,
                                                              @RequestParam(value = "desc",required = false)boolean desc);

    @PostMapping
    public Void insertBrandAndCategory(Brand brand, @RequestParam(value = "cids") List<Long> cids);

    // /brand/cid/76
    @GetMapping("cid/{cid}")
    public List<Brand> queryBrandsByCid3(@PathVariable("cid")Long cid3);

    @GetMapping("/{id}")
    public Brand queryBrandById(@PathVariable("id")Long id);
}
