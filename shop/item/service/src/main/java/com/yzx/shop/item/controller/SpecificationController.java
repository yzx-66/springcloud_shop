package com.yzx.shop.item.controller;

import com.yzx.shop.item.entity.SpecGroup;
import com.yzx.shop.item.entity.SpecParam;
import com.yzx.shop.item.entity.Spu;
import com.yzx.shop.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("spec")
public class SpecificationController {

    @Autowired
    private SpecificationService specificationService;

    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> querySpecGroupByCid(@PathVariable("cid")Long cid){
        List<SpecGroup> specGroups=specificationService.querySpecGroupByCid(cid);
        if(CollectionUtils.isEmpty(specGroups)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(specGroups);
    }

    @PostMapping("group")
    public ResponseEntity<List<SpecGroup>> insertSpecGroup(@RequestParam("cid")Long cid,@RequestParam("name")String name){
        specificationService.insertSpecGroup(cid,name);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> querySpecParanmByGidOrCid3(@RequestParam(value = "gid",required = false)Long gid,
                                                                     @RequestParam(value = "cid",required = false)Long cid3){
        List<SpecParam> specParams=specificationService.querySpecParanmByGidOrCid3(gid,cid3);
        if(CollectionUtils.isEmpty(specParams)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(specParams);
    }

    @GetMapping("{cid}")
    public ResponseEntity<List<SpecGroup>> querySpecGroupAndParamsByCid(@PathVariable("cid")Long cid){
        List<SpecGroup> specGroups=specificationService.querySpecGroupAndParamsByCid(cid);
        if(CollectionUtils.isEmpty(specGroups)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(specGroups);
    }
}
