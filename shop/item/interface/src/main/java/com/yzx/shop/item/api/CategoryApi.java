package com.yzx.shop.item.api;

import com.yzx.shop.item.entity.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("category")
public interface CategoryApi {

    @GetMapping("list")
    public List<Category> queryCategoriesByPid(@RequestParam(value = "pid",defaultValue = "0")Long pid);

    @GetMapping
    public List<String> queryNamesByIds(@RequestParam("ids")List<Long> ids);
}
