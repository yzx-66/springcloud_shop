package com.yzx.shop.item.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yzx.shop.commen.entity.PageResult;
import com.yzx.shop.item.bo.SpuBo;
import com.yzx.shop.item.entity.*;
import com.yzx.shop.item.mapper.*;
import com.yzx.shop.item.service.CategoryService;
import com.yzx.shop.item.service.GoodsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Resource
    private SpuMapper spuMapper;

    @Resource
    private SpuDetailMapper spuDetailMapper;

    @Resource
    private SkuMapper skuMapper;

    @Resource
    private StockMapper stockMapper;

    @Resource
    private BrandMapper brandMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    final String MSG_INSERT="insert";
    final String MSG_UPDATA="update";

    @Override
    public PageResult<SpuBo> querySpuList(String key, Boolean saleable, Integer page, Integer rows) {
        Example example=new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();

        if(StringUtils.isNotBlank(key)){
            criteria.andLike("title","%"+key+"%");
        }
        if(saleable!=null){
            criteria.andEqualTo("saleable",saleable);
        }
        criteria.andEqualTo("valid",true);

        if(rows!=-1){
            PageHelper.startPage(page,rows);
        }

        List<Spu> spuList = spuMapper.selectByExample(example);
        List<SpuBo> spuBoList = spuList.stream().map(spu -> {
            SpuBo spuBo = new SpuBo();
            BeanUtils.copyProperties(spu, spuBo);

            //String cname = categoryMapper.selectByPrimaryKey(spu.getCid1()).getName() + "/" + categoryMapper.selectByPrimaryKey(spu.getCid2()).getName() + "/" + categoryMapper.selectByPrimaryKey(spu.getCid3()).getName();
            List<Long> cidList= Arrays.asList(spuBo.getCid1(),spuBo.getCid2(),spuBo.getCid3());
            String cname=StringUtils.join(categoryService.queryNamesByIdList(cidList),"/");
            String bname = brandMapper.selectByPrimaryKey(spuBo.getBrandId()).getName();

            spuBo.setCname(cname);
            spuBo.setBname(bname);
            return spuBo;
        }).collect(Collectors.toList());

        return new PageResult<SpuBo>(rows==1?(new PageInfo<>(spuList).getTotal()):spuList.size(),spuBoList);
    }

    @Override
    @Transactional
    public void insertGoods(SpuBo spuBo) {
        spuBo.setCreateTime(new Date());
        spuBo.setLastUpdateTime(new Date());
        spuBo.setSaleable(true);
        spuBo.setValid(true);
        spuMapper.insert(spuBo);

        SpuDetail spuDetail=new SpuDetail();
        BeanUtils.copyProperties(spuBo.getSpuDetail(),spuDetail);
        spuDetail.setSpuId(spuBo.getId());
        spuDetailMapper.insert(spuDetail);

        insertSkusBySpuBo(spuBo);
        sendMsg(MSG_INSERT,spuBo.getId());
    }

    @Override
    @Transactional
    public void updateGoods(SpuBo spuBo) {
        Sku record=new Sku();
        record.setSpuId(spuBo.getId());
        List<Sku> skuList=skuMapper.select(record);

        skuList.forEach(s->{
            stockMapper.deleteByPrimaryKey(s.getId());
            skuMapper.deleteByPrimaryKey(s.getId());
        });

        spuBo.setLastUpdateTime(new Date());
        spuMapper.updateByPrimaryKeySelective(spuBo);
        spuDetailMapper.updateByPrimaryKey(spuBo.getSpuDetail());

        insertSkusBySpuBo(spuBo);
        sendMsg(MSG_UPDATA,spuBo.getId());
    }

    @Override
    public SpuDetail querySpuDetailBySid(Long spuId) {
        return spuDetailMapper.selectByPrimaryKey(spuId);
    }

    @Override
    public List<Sku> querySkuListBySpuId(Long spuId) {
        Sku record=new Sku();
        record.setSpuId(spuId);
        List<Sku> skuList = skuMapper.select(record);
        skuList.forEach(s->{
            s.setStock(stockMapper.selectByPrimaryKey(s.getId()).getStock());
        });

        return skuList;
    }

    @Override
    public Spu querySpuById(Long id) {
        return spuMapper.selectByPrimaryKey(id);
    }

    @Override
    public Sku querySkuById(Long skuId) {
        return skuMapper.selectByPrimaryKey(skuId);
    }

    public void insertSkusBySpuBo(SpuBo spuBo){
        spuBo.getSkus().forEach(s->{
            Sku sku=new Sku();
            BeanUtils.copyProperties(s,sku);
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(new Date());
            sku.setSpuId(spuBo.getId());
            skuMapper.insert(sku);

            Stock stock=new Stock();
            stock.setStock(sku.getStock());
            stock.setSkuId(sku.getId());
            stockMapper.insert(stock);
        });
    }

    public void sendMsg(String type,Long spuId){
        rabbitTemplate.convertAndSend("item."+type,spuId);
    }
}
