package com.yzx.shop.item.service.Impl;

import com.yzx.shop.item.entity.Category;
import com.yzx.shop.item.mapper.CategoryMapper;
import com.yzx.shop.item.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> queryCategoriesByPid(long pid) {
        Category record=new Category();
        record.setParentId(pid);
        return categoryMapper.select(record);
    }

    @Override
    public List<String> queryNamesByIdList(List<Long> ids) {
        List<Category> categoryList = categoryMapper.selectByIdList(ids);
        List<String> nameList = categoryList.stream().map(c -> c.getName()).collect(Collectors.toList());
        return nameList;
    }

    @Override
    public List<Category> queryAllCategorysByCid3(Long cid3) {
        Category c3=categoryMapper.selectByPrimaryKey(cid3);
        Category c2=categoryMapper.selectByPrimaryKey(c3.getParentId());
        Category c1=categoryMapper.selectByPrimaryKey(c2.getParentId());
        return Arrays.asList(c1, c2, c3);
    }
}
