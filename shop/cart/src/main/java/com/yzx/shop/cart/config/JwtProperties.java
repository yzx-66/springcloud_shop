package com.yzx.shop.cart.config;

import com.yzx.shop.auth.utils.JwtUtils;
import com.yzx.shop.auth.utils.RsaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.io.File;
import java.security.PublicKey;

@ConfigurationProperties(prefix = "shop.jwt")
public class JwtProperties {

    private String pubKeyPath;

    private String cookieName;

    private PublicKey publicKey;

    Logger logger= LoggerFactory.getLogger(JwtProperties.class);

    @PostConstruct
    public void init() throws Exception {
        File pub=new File(pubKeyPath);
        if(!pub.exists()){
            logger.info("获取公钥失败！");
            throw new Exception("获取公钥失败");
        }
        publicKey= RsaUtils.getPublicKey(pubKeyPath);
    }

    public String getPubKeyPath() {
        return pubKeyPath;
    }

    public void setPubKeyPath(String pubKeyPath) {
        this.pubKeyPath = pubKeyPath;
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }
}
