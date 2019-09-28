package com.yzx.shop.goodsWeb.listener;

import com.yzx.shop.goodsWeb.service.GoodsHtmlService;
import com.yzx.shop.goodsWeb.service.GoodsService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GoodsListener {

    @Autowired
    private GoodsHtmlService goodsHtmlService;


    @RabbitListener(bindings =@QueueBinding(
            value = @Queue(value = "item.web.html.create",durable = "true"),
            exchange = @Exchange(value = "shop.item.exchange",ignoreDeclarationExceptions = "true",type = "topic"),
            key = {"item.insert","item.update"}
            ))
    public void createHtml(Long id){
        goodsHtmlService.asyncSaveHtml(id);
    }

    @RabbitListener(bindings =@QueueBinding(
            value = @Queue(value = "item.web.html.delete",durable = "true"),
            exchange = @Exchange(value = "shop.item.exchange",ignoreDeclarationExceptions = "true",type = "topic"),
            key = {"item.delete"}
    ))
    public void deleteHtml(Long id){
        goodsHtmlService.deleteHtml(id);
    }
}
