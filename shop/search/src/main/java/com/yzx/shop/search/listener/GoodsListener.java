package com.yzx.shop.search.listener;

import com.yzx.shop.search.service.SearchService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GoodsListener {

    @Autowired
    private SearchService searchService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "shop.creat.index.quene",durable ="true",ignoreDeclarationExceptions ="true"),
            exchange = @Exchange(value = "shop.item.exchange",ignoreDeclarationExceptions ="true",type = "topic"),
            key = {"item.insert","item.update"}
    ))
    public void createIndex(Long id) throws IOException {
        if(id==null){
            return;
        }
        searchService.createIndex(id);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "shop.delete.index.quene",durable ="true",ignoreDeclarationExceptions ="true"),
            exchange = @Exchange(value = "shop.item.exchange",ignoreDeclarationExceptions ="true",type = "topic"),
            key = {"item.delete"}
    ))
    public void deleteIndex(Long id){
        if(id==null){
            return;
        }
        searchService.deleteIndex(id);
    }
}
