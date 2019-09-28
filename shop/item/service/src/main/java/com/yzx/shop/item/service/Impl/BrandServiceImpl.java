package com.yzx.shop.item.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yzx.shop.commen.entity.PageResult;
import com.yzx.shop.item.entity.Brand;
import com.yzx.shop.item.mapper.BrandMapper;
import com.yzx.shop.item.service.BrandService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Resource
    private BrandMapper brandMapper;

    @Override
    public PageResult<Brand> queryBrandByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
        Example example=new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();

        if(StringUtils.isNotBlank(key)){
            criteria.andLike("name","%"+key+"%").orEqualTo("letter",key);
        }

        if(StringUtils.isNotBlank(sortBy)){
            example.setOrderByClause(sortBy+" "+(desc?"desc":"asc"));
        }

        PageHelper.startPage(page,rows);
        List<Brand> brandList = brandMapper.selectByExample(example);
        PageInfo<Brand> pageInfo=new PageInfo<>(brandList);

        return new PageResult<Brand>(pageInfo.getTotal(),pageInfo.getList());
    }

    @Override
    @Transactional
    public void insertBrandAndCategory(Brand brand, List<Long> cids) {
        brandMapper.insertSelective(brand);
        cids.forEach((cid)->brandMapper.insertBrandAndCategory(cid,brand.getId()));
    }

    @Override
    public List<Brand> queryBrandsByCid3(Long cid3) {
        return brandMapper.queryBrandsByCid3(cid3);
    }

    @Override
    public Brand queryBrandById(Long id) {
        return brandMapper.selectByPrimaryKey(id);
    }
}
