package com.yzx.shop.cart.interceptor;

import com.yzx.shop.auth.entity.UserInfo;
import com.yzx.shop.auth.utils.JwtUtils;
import com.yzx.shop.cart.config.JwtProperties;
import com.yzx.shop.commen.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@EnableConfigurationProperties(JwtProperties.class)
@Component
public class LoginInterceptor implements HandlerInterceptor {

    public static ThreadLocal<UserInfo> userInfoThreadLocal=new ThreadLocal<>();

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = CookieUtils.getCookieValue(request, jwtProperties.getCookieName());
        UserInfo userInfo=null;
        try{
            userInfo = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
        }catch (Exception e){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        if(userInfo==null){
            return false;
        }
        userInfoThreadLocal.set(userInfo);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        userInfoThreadLocal.remove();
    }
}
