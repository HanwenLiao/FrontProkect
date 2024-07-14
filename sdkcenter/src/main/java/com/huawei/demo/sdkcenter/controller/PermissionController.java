package com.huawei.demo.sdkcenter.controller;

import com.huawei.demo.sdkcenter.entity.dao.Permission;
import com.huawei.demo.sdkcenter.entity.dao.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@CrossOrigin(maxAge = 3600)
@RestController
public class PermissionController {

    @Autowired
    private PermissionMapper permissionMapper;

    @GetMapping("/permission")
    public List query(){
        List<Permission> list = permissionMapper.selectList(null);
        System.out.println(list);
        return list;
    }

}
