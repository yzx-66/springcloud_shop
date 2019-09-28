package com.yzx.shop.item.service;

import com.yzx.shop.item.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> queryCategoriesByPid(long pid);
    List<String> queryNamesByIdList(List<Long>ids);

    List<Category> queryAllCategorysByCid3(Long cid3);
}
