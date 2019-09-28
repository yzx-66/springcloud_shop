package com.yzx.shop.item.service;

import com.yzx.shop.item.entity.SpecGroup;
import com.yzx.shop.item.entity.SpecParam;

import java.util.List;

public interface SpecificationService {
    List<SpecGroup> querySpecGroupByCid(Long cid);

    void insertSpecGroup(Long cid,String name);

    List<SpecParam> querySpecParanmByGidOrCid3(Long gid,Long cid3);

    List<SpecGroup> querySpecGroupAndParamsByCid(Long cid);
}
