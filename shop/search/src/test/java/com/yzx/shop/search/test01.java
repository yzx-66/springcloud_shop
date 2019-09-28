package com.yzx.shop.search;

import com.yzx.shop.search.client.BrandClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class test01 {

    @Autowired
    private BrandClient brandClient;

    @Test
    public void test(){
        System.out.println(brandClient.queryBrandsByCid3(Long.parseLong("76")));
    }
}
