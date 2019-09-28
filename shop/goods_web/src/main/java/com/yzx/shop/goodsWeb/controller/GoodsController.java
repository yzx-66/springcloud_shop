package com.yzx.shop.goodsWeb.controller;

import com.yzx.shop.goodsWeb.service.GoodsHtmlService;
import com.yzx.shop.goodsWeb.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("item")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsHtmlService goodsHtmlService;

    @GetMapping("/{id}.html")
    public ModelAndView getGoodsPage(@PathVariable("id")Long id, ModelAndView modelAndView){
        modelAndView.setViewName("item");
        modelAndView.addAllObjects(goodsService.loadData(id));

        goodsHtmlService.asyncSaveHtml(id);

        return modelAndView;
    }
}
