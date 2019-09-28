package com.yzx.shop.order.service.api;

import com.yzx.shop.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "shop-gateway", path = "/api/item")
public interface GoodsService extends GoodsApi {
}
