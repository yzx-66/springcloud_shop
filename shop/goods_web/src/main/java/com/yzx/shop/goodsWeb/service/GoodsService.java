package com.yzx.shop.goodsWeb.service;

import com.yzx.shop.item.entity.Spu;

import java.util.Map;

public interface GoodsService {

    Map<String,Object> loadData(Long spuId);
}
