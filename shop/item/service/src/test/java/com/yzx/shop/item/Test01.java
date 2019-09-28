package com.yzx.shop.item;

import com.yzx.shop.item.entity.Sku;
import com.yzx.shop.item.entity.SpuDetail;
import com.yzx.shop.item.mapper.SkuMapper;
import com.yzx.shop.item.mapper.SpuDetailMapper;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Test01 {

    @Resource
    private SpuDetailMapper spuDetailMapper;

    @Resource
    private SkuMapper skuMapper;

    @Test
    public void test01(){
        List<Sku> skuList = skuMapper.selectAll();
        skuList.forEach(sku ->{
            String imamge=sku.getImages();
            String res= StringUtils.replace(imamge,"image.leyou.com","image.shop.com");
            sku.setImages(res);
            skuMapper.updateByPrimaryKey(sku);
        });
    }
}
