package com.yzx.shop.cart.service;

import com.yzx.shop.cart.entity.Cart;

import java.util.List;

public interface CartService {
    void insertCart(Cart cart);

    void updateCart(Cart cart);

    List<Cart> queryCarts();

    void deleteCart(Long skuId);
}
