package com.yzx.shop.goodsWeb.service;

public interface GoodsHtmlService {

    void createHtml(Long spuId);
    void asyncSaveHtml(Long spuId);

    void deleteHtml(Long id);
}
