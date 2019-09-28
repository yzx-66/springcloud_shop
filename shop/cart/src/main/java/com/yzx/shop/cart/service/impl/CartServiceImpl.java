package com.yzx.shop.cart.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yzx.shop.auth.entity.UserInfo;
import com.yzx.shop.cart.client.GoodsClient;
import com.yzx.shop.cart.entity.Cart;
import com.yzx.shop.cart.interceptor.LoginInterceptor;
import com.yzx.shop.cart.service.CartService;
import com.yzx.shop.commen.utils.JsonUtils;
import com.yzx.shop.item.entity.Sku;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private GoodsClient goodsClient;

    private final String PREFIX="user:cart:";

    @Override
    public void insertCart(Cart cart) {
        UserInfo userInfo= LoginInterceptor.userInfoThreadLocal.get();
        HashOperations<String,Object,Object> hash = redisTemplate.opsForHash();
        Boolean isHave = hash.hasKey(PREFIX + userInfo.getId(), String.valueOf(cart.getSkuId()));

        if(isHave){
            Object jsonValue = hash.get(PREFIX + userInfo.getId(), String.valueOf(cart.getSkuId()));
            int num=cart.getNum();
            cart = JsonUtils.parse((String) jsonValue, Cart.class);
            cart.setNum(num+cart.getNum());
        }else {
            Sku sku = goodsClient.querySkuById(cart.getSkuId());
            cart.setUserId(userInfo.getId());
            cart.setTitle(sku.getTitle());
            cart.setOwnSpec(sku.getOwnSpec());
            cart.setImage(StringUtils.isBlank(sku.getImages()) ? "" : StringUtils.split(sku.getImages(), ",")[0]);
            cart.setPrice(sku.getPrice());
        }

        hash.put(PREFIX + userInfo.getId(),String.valueOf(cart.getSkuId()), JsonUtils.serialize(cart));
    }

    @Override
    public void updateCart(Cart cart) {
        UserInfo userInfo= LoginInterceptor.userInfoThreadLocal.get();
        HashOperations<String,Object,Object> hash = redisTemplate.opsForHash();
        int num=cart.getNum();
        cart= JsonUtils.parse((String) hash.get(PREFIX + userInfo.getId(), String.valueOf(cart.getSkuId())), Cart.class);

        cart.setNum(num);
        hash.put(PREFIX + userInfo.getId(),String.valueOf(cart.getSkuId()), JsonUtils.serialize(cart));
    }

    @Override
    public List<Cart> queryCarts() {
        UserInfo userInfo= LoginInterceptor.userInfoThreadLocal.get();
        HashOperations<String,Object,Object> hash = redisTemplate.opsForHash();
        List<Object> carts = hash.values(PREFIX + userInfo.getId());
        return carts.stream().map(cart-> JsonUtils.parse((String) cart,Cart.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteCart(Long skuId) {
        UserInfo userInfo= LoginInterceptor.userInfoThreadLocal.get();
        HashOperations<String,Object,Object> hash = redisTemplate.opsForHash();
        hash.delete(PREFIX+userInfo.getId(),String.valueOf(skuId));
    }
}
