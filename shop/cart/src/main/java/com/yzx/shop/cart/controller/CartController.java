package com.yzx.shop.cart.controller;

import com.yzx.shop.cart.entity.Cart;
import com.yzx.shop.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<Void> insertCart(@RequestBody Cart cart){
        cartService.insertCart(cart);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateCart(@RequestBody Cart cart){
        cartService.updateCart(cart);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Cart>> queryCarts(){
        List<Cart> carts = this.cartService.queryCarts();
        if (CollectionUtils.isEmpty(carts)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(carts);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCart(@RequestBody Cart cart){
        this.cartService.deleteCart(cart.getSkuId());
        return ResponseEntity.ok().build();
    }
}
