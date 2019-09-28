package com.yzx.shop.user.api;

import com.yzx.shop.user.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

public interface UserApi {

    @GetMapping("check/{data}/{type}")
    public Boolean checkUserData(@PathVariable("data")String  data,@PathVariable("type")Integer type);

    @PostMapping("code")
    public Void sendVerifyCode(String phone);

    @PostMapping("register")
    public Void register(@Valid User user, @RequestParam("code") String code);

    @GetMapping("query")
    public User queryUser(@RequestParam("username") String username, @RequestParam("password") String password );
}
