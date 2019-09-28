package com.yzx.shop.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.yzx.shop.auth.utils.JwtUtils;
import com.yzx.shop.commen.utils.CookieUtils;
import com.yzx.shop.gateway.config.FilterPropertiess;
import com.yzx.shop.gateway.config.JwtProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
@EnableConfigurationProperties({JwtProperties.class, FilterPropertiess.class})
public class LoginFilter extends ZuulFilter {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private FilterPropertiess filterPropertiess;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    @Override
    public boolean shouldFilter() {
        List<String> allowPaths=filterPropertiess.getAllowPaths();
        RequestContext context=RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String url=request.getRequestURI();

        for (String allowPath : allowPaths) {
            if(StringUtils.contains(url, allowPath)){
                return false;
            }
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context=RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String token= CookieUtils.getCookieValue(request,jwtProperties.getCookieName());

        try {
            JwtUtils.getInfoFromToken(token,jwtProperties.getPublicKey());
        } catch (Exception e) {
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
        }

        return null;
    }


}
