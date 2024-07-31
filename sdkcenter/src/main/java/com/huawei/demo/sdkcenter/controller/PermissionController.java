//package com.huawei.demo.sdkcenter.controller;
//
//import com.huawei.demo.sdkcenter.entity.dao.Permission;
//import com.huawei.demo.sdkcenter.entity.dao.mapper.PermissionMapper;
//import com.huawei.demo.sdkcenter.entity.resp.PermissionResp;
//import com.huawei.demo.sdkcenter.constant.enums.PermissionType;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@CrossOrigin(maxAge = 3600)
//@RestController
//public class PermissionController {
//
//    @Autowired
//    private PermissionMapper permissionMapper;
//
//    @GetMapping("/permissions")
//    public List<PermissionResp> query() {
//        List<Permission> list = permissionMapper.selectList(null);
//        return list.stream().map(this::convertToResp).collect(Collectors.toList());
//    }
//
//    @PutMapping("/permissions/{id}/toggle-sensitive")
//    public PermissionResp toggleSensitive(@PathVariable Long id) {
//        Permission permission = permissionMapper.selectById(id);
//        if (permission != null) {
//            permission.setIsSensitive(permission.getIsSensitive() == 1 ? 0 : 1);
//            permissionMapper.updateById(permission);
//        }
//        return convertToResp(permission);
//    }
//
//    private PermissionResp convertToResp(Permission permission) {
//        PermissionResp resp = new PermissionResp();
//        resp.setId(permission.getId());
//        resp.setPermissionName(permission.getPermissionName());
//        resp.setIsSensitive(permission.getIsSensitive());
//        resp.setDescription(permission.getDescription());
//        PermissionType permissionType = permission.getPermissionType();
//        resp.setPermissionType(permissionType != null ? permissionType.getDescription() : null);
//        return resp;
//    }
//}
package com.huawei.demo.sdkcenter.controller;

import com.huawei.demo.sdkcenter.entity.dao.Permission;
import com.huawei.demo.sdkcenter.entity.dao.mapper.PermissionMapper;
import com.huawei.demo.sdkcenter.entity.resp.PermissionResp;
import com.huawei.demo.sdkcenter.constant.enums.PermissionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(maxAge = 3600)
@RestController
public class PermissionController {

    @Autowired
    private PermissionMapper permissionMapper;

    @GetMapping("/permissions")
    public List<PermissionResp> query() {
        List<Permission> list = permissionMapper.selectList(null);
        return list.stream().map(this::convertToResp).collect(Collectors.toList());
    }

    @PutMapping("/permissions/{id}/toggle-sensitive")
    public PermissionResp toggleSensitive(@PathVariable Long id) {
        Permission permission = permissionMapper.selectById(id);
        if (permission != null) {
            permission.setIsSensitive(permission.getIsSensitive() == 1 ? 0 : 1);
            permissionMapper.updateById(permission);
        }
        return convertToResp(permission);
    }

    @PutMapping("/permissions/{id}/update")
    public PermissionResp updatePermission(@PathVariable Long id, @RequestBody PermissionResp updatedPermission) {
        Permission permission = permissionMapper.selectById(id);
        if (permission != null) {
            permission.setPermissionName(updatedPermission.getPermissionName());
            permission.setIsSensitive(updatedPermission.getIsSensitive());
            permission.setDescription(updatedPermission.getDescription());
            PermissionType permissionType = PermissionType.valueOf(updatedPermission.getPermissionType());
            permission.setPermissionType(permissionType);
            permissionMapper.updateById(permission);
        }
        return convertToResp(permission);
    }
    @DeleteMapping("/permissions/{id}/delete")
    public void deletePermission(@PathVariable Long id) {
        permissionMapper.deleteById(id);
    }

    @PostMapping("/permissions/add")
    public PermissionResp addPermission(@RequestBody PermissionResp newPermission) {
        Permission permission = new Permission();
        permission.setPermissionName(newPermission.getPermissionName());
        permission.setIsSensitive(newPermission.getIsSensitive());
        permission.setDescription(newPermission.getDescription());
        permission.setPermissionType(PermissionType.valueOf(newPermission.getPermissionType()));
        permissionMapper.insert(permission);
        return convertToResp(permission);
    }

    private PermissionResp convertToResp(Permission permission) {
        PermissionResp resp = new PermissionResp();
        resp.setId(permission.getId());
        resp.setPermissionName(permission.getPermissionName());
        resp.setIsSensitive(permission.getIsSensitive());
        resp.setDescription(permission.getDescription());
        PermissionType permissionType = permission.getPermissionType();
        resp.setPermissionType(permissionType != null ? permissionType.name() : null);
        return resp;
    }
}