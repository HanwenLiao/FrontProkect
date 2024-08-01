package com.huawei.demo.sdkcenter.service;

import com.huawei.demo.sdkcenter.entity.dao.Permission;
import com.huawei.demo.sdkcenter.entity.dao.mapper.PermissionMapper;
import com.huawei.demo.sdkcenter.entity.req.PermissionReq;
import com.huawei.demo.sdkcenter.entity.resp.PermissionResp;
import com.huawei.demo.sdkcenter.constant.enums.PermissionType;
import com.huawei.demo.sdkcenter.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    public ResultBean<List<PermissionResp>> getAllPermissions() {
        List<Permission> list = permissionMapper.selectList(null);
        List<PermissionResp> result = list.stream().map(this::convertToResp).collect(Collectors.toList());
        return ResultBean.success(result);
    }

    public ResultBean<PermissionResp> toggleSensitive(Long id) {
        Permission permission = permissionMapper.selectById(id);
        if (permission != null) {
            permission.setIsSensitive(permission.getIsSensitive() == 1 ? 0 : 1);
            permissionMapper.updateById(permission);
        }
        return ResultBean.success(convertToResp(permission));
    }

    public ResultBean<PermissionResp> updatePermission(Long id, PermissionReq updatedPermission) {
        Permission permission = permissionMapper.selectById(id);
        if (permission != null) {
            permission.setPermissionName(updatedPermission.getPermissionName());
            permission.setIsSensitive(updatedPermission.getIsSensitive());
            permission.setDescription(updatedPermission.getDescription());
            permission.setPermissionTypeNum(PermissionType.valueOf(updatedPermission.getPermissionType()));
            permissionMapper.updateById(permission);
        }
        return ResultBean.success(convertToResp(permission));
    }

    public ResultBean<Void> deletePermission(Long id) {
        permissionMapper.deleteById(id);
        return ResultBean.success();
    }

    public ResultBean<PermissionResp> addPermission(PermissionReq newPermission) {
        Permission permission = new Permission();
        permission.setPermissionName(newPermission.getPermissionName());
        permission.setIsSensitive(newPermission.getIsSensitive());
        permission.setDescription(newPermission.getDescription());
        permission.setPermissionTypeNum(PermissionType.valueOf(newPermission.getPermissionType()));
        permissionMapper.insert(permission);
        return ResultBean.success(convertToResp(permission));
    }

    private PermissionResp convertToResp(Permission permission) {
        PermissionResp resp = new PermissionResp();
        resp.setId(String.valueOf(permission.getId()));
        resp.setPermissionName(permission.getPermissionName());
        resp.setIsSensitive(permission.getIsSensitive());
        resp.setDescription(permission.getDescription());
        PermissionType permissionType = permission.getPermissionType();
        resp.setPermissionType(permissionType != null ? permissionType.name() : null);
        return resp;
    }
}