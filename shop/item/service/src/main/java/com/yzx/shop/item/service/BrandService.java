package com.yzx.shop.item.service;

import com.yzx.shop.commen.entity.PageResult;
import com.yzx.shop.item.entity.Brand;

import java.util.List;

public interface BrandService {

    PageResult<Brand> queryBrandByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc);

    void insertBrandAndCategory(Brand brand, List<Long> cids);

    List<Brand> queryBrandsByCid3(Long cid3);

    Brand queryBrandById(Long id);
}
