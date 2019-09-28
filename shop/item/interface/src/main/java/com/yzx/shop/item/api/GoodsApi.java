package com.yzx.shop.item.api;

import com.yzx.shop.commen.entity.PageResult;
import com.yzx.shop.item.bo.SpuBo;
import com.yzx.shop.item.entity.Sku;
import com.yzx.shop.item.entity.Spu;
import com.yzx.shop.item.entity.SpuDetail;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface GoodsApi {
    @GetMapping("spu/page")
    public PageResult<SpuBo> querySpuList(@RequestParam(value = "key",required = false)String key,
                                                          @RequestParam(value = "saleable",required = false)Boolean saleable,
                                                          @RequestParam(value = "page")Integer page,
                                                          @RequestParam(value = "rows")Integer rows);

    // /goods
    @PostMapping("goods")
    public Void insertGoods(@RequestBody SpuBo spuBo);

    @PutMapping("goods")
    public Void updateGoods(@RequestBody SpuBo spuBo);

    // spu/detail/190
    @GetMapping("spu/detail/{spuid}")
    public SpuDetail querySpuDetailBySid(@PathVariable("spuid")Long spuId);

    //sku/list?id=3
    @GetMapping("sku/list")
    public List<Sku> querySkuListBySpuId(@RequestParam("id")Long spuId);

    @GetMapping("spu/{id}")
    public Spu querySpuById(@PathVariable("id")Long id);

    @GetMapping("sku/{skuId}")
    public Sku querySkuById(@PathVariable("skuId") Long skuId);
}
