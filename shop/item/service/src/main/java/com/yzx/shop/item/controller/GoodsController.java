package com.yzx.shop.item.controller;

import com.yzx.shop.commen.entity.PageResult;
import com.yzx.shop.item.bo.SpuBo;
import com.yzx.shop.item.entity.Sku;
import com.yzx.shop.item.entity.Spu;
import com.yzx.shop.item.entity.SpuDetail;
import com.yzx.shop.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    // /spu/page?key=&saleable=true&page=1&rows=5
    @GetMapping("spu/page")
    public ResponseEntity<PageResult<SpuBo>> querySpuList(@RequestParam(value = "key",required = false)String key,
                                                          @RequestParam(value = "saleable",required = false)Boolean saleable,
                                                          @RequestParam(value = "page")Integer page,
                                                          @RequestParam(value = "rows")Integer rows){
        PageResult<SpuBo> spuPageResult=goodsService.querySpuList(key,saleable,page,rows);
        if(CollectionUtils.isEmpty(spuPageResult.getItems())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(spuPageResult);
    }

    // /goods
    @PostMapping("goods")
    public ResponseEntity<Void> insertGoods(@RequestBody SpuBo spuBo){
        goodsService.insertGoods(spuBo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("goods")
    public ResponseEntity<Void> updateGoods(@RequestBody SpuBo spuBo){
        goodsService.updateGoods(spuBo);
        return ResponseEntity.ok(null);
    }

    // spu/detail/190
    @GetMapping("spu/detail/{spuid}")
    public ResponseEntity<SpuDetail> querySpuDetailBySid(@PathVariable("spuid")Long spuId){
        SpuDetail spuDetail=goodsService.querySpuDetailBySid(spuId);
        if(spuDetail==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(spuDetail);
    }

    //sku/list?id=3
    @GetMapping("sku/list")
    public ResponseEntity<List<Sku>> querySkuListBySpuId(@RequestParam("id")Long spuId){
        List<Sku> skuList=goodsService.querySkuListBySpuId(spuId);
        if(CollectionUtils.isEmpty(skuList)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(skuList);
    }

    @GetMapping("spu/{id}")
    public ResponseEntity<Spu> querySpuById(@PathVariable("id")Long id){
        Spu spu=goodsService.querySpuById(id);
        if(spu==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(spu);
    }

    @GetMapping("sku/{skuId}")
    public ResponseEntity<Sku> querySkuById(@PathVariable("skuId") Long skuId){
        Sku sku=goodsService.querySkuById(skuId);
        if(sku==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sku);
    }
}
