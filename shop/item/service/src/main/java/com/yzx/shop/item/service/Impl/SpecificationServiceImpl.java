package com.yzx.shop.item.service.Impl;

import com.yzx.shop.item.entity.SpecGroup;
import com.yzx.shop.item.entity.SpecParam;
import com.yzx.shop.item.mapper.SpecGroupMapper;
import com.yzx.shop.item.mapper.SpecParamMapper;
import com.yzx.shop.item.service.SpecificationService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SpecificationServiceImpl implements SpecificationService {

    @Resource
    private SpecGroupMapper specGroupMapper;

    @Resource
    private SpecParamMapper specParamMapper;


    @Override
    public List<SpecGroup> querySpecGroupByCid(Long cid) {
        SpecGroup record=new SpecGroup();
        record.setCid(cid);
        return specGroupMapper.select(record);
    }

    @Override
    public void insertSpecGroup(Long cid,String name) {
        SpecGroup specGroup=new SpecGroup();
        specGroup.setCid(cid);
        specGroup.setName(name);
        specGroupMapper.insert(specGroup);
    }

    @Override
    public List<SpecParam> querySpecParanmByGidOrCid3(Long gid,Long cid3){
        SpecParam record=new SpecParam();
        if(gid!=null){
            record.setGroupId(gid);
        }
        if(cid3!=null){
            record.setCid(cid3);
        }
        return specParamMapper.select(record);
    }

    @Override
    public List<SpecGroup> querySpecGroupAndParamsByCid(Long cid) {
        SpecGroup record=new SpecGroup();
        record.setCid(cid);
        List<SpecGroup> specGroups = specGroupMapper.select(record);

        specGroups.forEach(specGroup -> {
            SpecParam param=new SpecParam();
            param.setGroupId(specGroup.getId());
            List<SpecParam> specParams = specParamMapper.select(param);
            specGroup.setParams(specParams);
        });

        return specGroups;
    }


}
