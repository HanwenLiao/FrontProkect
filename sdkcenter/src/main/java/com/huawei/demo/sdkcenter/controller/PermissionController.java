package com.huawei.demo.sdkcenter.controller;

import com.huawei.demo.sdkcenter.entity.req.PermissionReq;
import com.huawei.demo.sdkcenter.entity.resp.PermissionResp;
import com.huawei.demo.sdkcenter.service.PermissionService;
import com.huawei.demo.sdkcenter.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("api/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping
    public ResultBean<List<PermissionResp>> query() {
        return permissionService.getAllPermissions();
    }

    @PutMapping("/{id}/toggle-sensitive")
    public ResultBean<PermissionResp> toggleSensitive(@PathVariable Long id) {
        return permissionService.toggleSensitive(id);
    }

    @PutMapping("/{id}/update")
    public ResultBean<PermissionResp> updatePermission(@PathVariable Long id, @RequestBody PermissionReq updatedPermission) {
        return permissionService.updatePermission(id, updatedPermission);
    }

    @DeleteMapping("/{id}/delete")
    public ResultBean<Void> deletePermission(@PathVariable Long id) {
        return permissionService.deletePermission(id);
    }

    @PostMapping("/add")
    public ResultBean<PermissionResp> addPermission(@RequestBody PermissionReq newPermission) {
        return permissionService.addPermission(newPermission);
    }
}