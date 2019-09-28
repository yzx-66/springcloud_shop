package com.yzx.shop.auth.controller;

import com.yzx.shop.auth.config.JwtProperties;
import com.yzx.shop.auth.entity.UserInfo;
import com.yzx.shop.auth.service.AuthService;
import com.yzx.shop.auth.utils.JwtUtils;
import com.yzx.shop.commen.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@EnableConfigurationProperties(JwtProperties.class)
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("accredit")
    public ResponseEntity<Void> authentication(@RequestParam("username")String username,
                                               @RequestParam("password")String password,
                                               HttpServletRequest request,
                                               HttpServletResponse response){
        String token=authService.authentication(username,password);
        if(StringUtils.isBlank(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        CookieUtils.setCookie(request,response,jwtProperties.getCookieName(),token,jwtProperties.getExpire(),"utf-8",true);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("verify")
    public ResponseEntity<UserInfo> getUserInfo(@CookieValue("SHOP_TOKEN")String token,HttpServletRequest request, HttpServletResponse response){
        if(StringUtils.isBlank(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserInfo userInfo=null;
        try {
            userInfo= JwtUtils.getInfoFromToken(token,jwtProperties.getPublicKey());
            String newToken = JwtUtils.generateToken(userInfo, jwtProperties.getPrivateKey(), jwtProperties.getExpire());
            CookieUtils.setCookie(request,response,jwtProperties.getCookieName(),newToken,jwtProperties.getExpire(),"utf-8",true);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok(userInfo);
    }

}
