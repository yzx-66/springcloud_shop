package com.yzx.shop.item.service;

import com.yzx.shop.commen.entity.PageResult;
import com.yzx.shop.item.bo.SpuBo;
import com.yzx.shop.item.entity.Brand;
import com.yzx.shop.item.entity.Sku;
import com.yzx.shop.item.entity.Spu;
import com.yzx.shop.item.entity.SpuDetail;

import java.util.List;

public interface GoodsService {
    PageResult<SpuBo> querySpuList(String key, Boolean saleable, Integer page, Integer rows);

    void insertGoods(SpuBo spuBo);

    void updateGoods(SpuBo spuBo);

    SpuDetail querySpuDetailBySid(Long spuId);

    List<Sku> querySkuListBySpuId(Long spuId);

    Spu querySpuById(Long id);

    Sku querySkuById(Long skuId);
}
