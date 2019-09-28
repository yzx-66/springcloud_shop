package com.yzx.shop.auth;

import com.yzx.shop.auth.utils.RsaUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Test01 {

    //生成公钥的路径
    private static final String pubKeyPath = "C:\\tmp\\rsa\\rsa.pub";
    //生成私钥的路径
    private static final String priKeyPath = "C:\\tmp\\rsa\\rsa.pri";

    @Test
    public void testRsa() throws Exception {
        //生成公钥和私钥
        //234指的时加密时的盐 越复杂越好
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
    }

}
