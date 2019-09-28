package com.yzx.shop.search.entity;

import com.yzx.shop.commen.entity.PageResult;
import com.yzx.shop.item.entity.Brand;

import java.util.List;
import java.util.Map;

public class SearchResult extends PageResult<Goods> {

    private List<Brand> brands;

    private List<Map<String, Object>> categories;

    private List<Map<String, Object>> specs;

    public SearchResult(Long total, List<Goods> items, List<Brand> brands, List<Map<String, Object>> categories) {
        super(total, items);
        this.brands = brands;
        this.categories = categories;
    }

    public SearchResult(Long total, Integer totalPage, List<Goods> items, List<Brand> brands, List<Map<String, Object>> categories) {
        super(total, totalPage, items);
        this.brands = brands;
        this.categories = categories;
    }

    public SearchResult(Long total, List<Goods> items, List<Brand> brands, List<Map<String, Object>> categories, List<Map<String, Object>> specs) {
        super(total, items);
        this.brands = brands;
        this.categories = categories;
        this.specs = specs;
    }

    public SearchResult(Long total, Integer totalPage, List<Goods> items, List<Brand> brands, List<Map<String, Object>> categories, List<Map<String, Object>> specs) {
        super(total, totalPage, items);
        this.brands = brands;
        this.categories = categories;
        this.specs = specs;
    }

    public List<Map<String, Object>> getSpecs() {
        return specs;
    }

    public void setSpecs(List<Map<String, Object>> specs) {
        this.specs = specs;
    }

    public List<Map<String, Object>> getCategories() {
        return categories;
    }

    public void setCategories(List<Map<String, Object>> categories) {
        this.categories = categories;
    }

    public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }
}
