package com.yzx.shop.user.service;

import com.yzx.shop.user.entity.User;

public interface UserService {
    Boolean checkUserData(String data, Integer type);

    Boolean sendVerifyCode(String phone);

    Boolean register(User user, String code);

    User queryUser(String username, String password);
}
