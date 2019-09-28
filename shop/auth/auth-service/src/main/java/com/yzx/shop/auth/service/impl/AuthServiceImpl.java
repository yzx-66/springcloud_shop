package com.yzx.shop.auth.service.impl;

import com.yzx.shop.auth.client.UserClient;
import com.yzx.shop.auth.config.JwtProperties;
import com.yzx.shop.auth.entity.UserInfo;
import com.yzx.shop.auth.service.AuthService;
import com.yzx.shop.auth.utils.JwtUtils;
import com.yzx.shop.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@EnableConfigurationProperties(JwtProperties.class)
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public String authentication(String username, String password) {
        User user = userClient.queryUser(username, password);
        if(user==null){
            return null;
        }

        UserInfo userInfo=new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setUsername(user.getUsername());

        String token=null;
        try {
            token = JwtUtils.generateToken(userInfo, jwtProperties.getPrivateKey(), jwtProperties.getExpire());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }
}
