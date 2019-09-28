package com.yzx.shop.goodsWeb.service.impl;

import com.yzx.shop.goodsWeb.service.GoodsHtmlService;
import com.yzx.shop.goodsWeb.service.GoodsService;
import com.yzx.shop.goodsWeb.util.ThreadPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;

@Service
public class GoodsHtmlServiceImpl implements GoodsHtmlService {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public void createHtml(Long spuId) {
        Map<String, Object> data = goodsService.loadData(spuId);
        Context context=new Context();
        context.setVariables(data);

        File file=new File("C:\\fuwuqi\\nginx\\nginx-1.14.0\\nginx-1.14.0\\html\\item\\"+spuId+".html");
        if(file.exists()){
            file.delete();
        }
        PrintWriter writer=null;
        try {
            writer=new PrintWriter(file);
            templateEngine.process("item",context,writer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(writer!=null){
                writer.close();
            }
        }
    }

    @Override
    public void asyncSaveHtml(Long spuId) {
        ThreadPoolUtil.excute(()->createHtml(spuId));
    }

    @Override
    public void deleteHtml(Long id) {
        File file=new File("C:\\fuwuqi\\nginx\\nginx-1.14.0\\nginx-1.14.0\\html\\item\\"+id+".html");
        file.delete();
    }
}
