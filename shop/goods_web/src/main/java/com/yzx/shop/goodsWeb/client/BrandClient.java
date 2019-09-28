package com.yzx.shop.goodsWeb.client;

import com.yzx.shop.item.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("item-service")
public interface BrandClient extends BrandApi {
}
