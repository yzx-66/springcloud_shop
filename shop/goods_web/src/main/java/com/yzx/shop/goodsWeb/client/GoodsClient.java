package com.yzx.shop.goodsWeb.client;

import com.yzx.shop.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {
}
