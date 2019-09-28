package com.yzx.shop.goodsWeb.client;

import com.yzx.shop.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("item-service")
public interface CategoryClient extends CategoryApi {
}
