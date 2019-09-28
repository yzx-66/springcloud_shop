package com.yzx.shop.item.api;

import com.yzx.shop.item.entity.SpecGroup;
import com.yzx.shop.item.entity.SpecParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("spec")
public interface SpecificationApi {
    @GetMapping("groups/{cid}")
    public List<SpecGroup> querySpecGroupByCid(@PathVariable("cid")Long cid);

    @PostMapping("group")
    public List<SpecGroup> insertSpecGroup(@RequestParam("cid")Long cid, @RequestParam("name")String name);

    @GetMapping("params")
    public List<SpecParam> querySpecParanmByGidOrCid3(@RequestParam(value = "gid",required = false)Long gid,
                                                      @RequestParam(value = "cid",required = false)Long cid3);

    @GetMapping("{cid}")
    public List<SpecGroup> querySpecGroupAndParamsByCid(@PathVariable("cid")Long cid);
}
