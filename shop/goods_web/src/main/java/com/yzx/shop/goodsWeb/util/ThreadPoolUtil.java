package com.yzx.shop.goodsWeb.util;

import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolUtil {

    final static ExecutorService es= Executors.newFixedThreadPool(10);

    public static void excute(Runnable runnable){
        es.submit(runnable);
    }
}
