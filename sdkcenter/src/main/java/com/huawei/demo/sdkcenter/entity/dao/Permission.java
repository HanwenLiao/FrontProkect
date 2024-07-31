package com.huawei.demo.sdkcenter.entity.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huawei.demo.sdkcenter.constant.enums.PermissionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("dict_permission")
public class Permission {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(value = "permission_name")
    private String permissionName;

    @TableField(value = "is_sensitive")
    private Integer isSensitive;

    @TableField(value = "permission_type")
    private Integer permissionTypeValue;

    @TableField(value = "description")
    private String description;

    @JsonIgnore
    public PermissionType getPermissionType() {
        if (this.permissionTypeValue == null) {
            return null;
        }
        return PermissionType.fromValue(this.permissionTypeValue);
    }

    public void setPermissionType(PermissionType permissionType) {
        this.permissionTypeValue = permissionType != null ? permissionType.getValue() : null;
    }
}