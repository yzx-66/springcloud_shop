package com.yzx.shop.cart.client;

import com.yzx.shop.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("ITEM-SERVICE")
public interface GoodsClient extends GoodsApi {

}
