package com.huawei.demo.sdkcenter.entity.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huawei.demo.sdkcenter.constant.enums.PermissionType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.Timestamp;

@Getter
@Setter
@TableName("dict_permission")
@MappedTypes(PermissionType.class)
public class Permission {
    @TableId(value = "permission_id", type = IdType.ASSIGN_ID)
    private Long permissionId;

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
            return null; // 或者返回一个默认值，比如 PermissionType.TYPE_0
        }
        return PermissionType.fromValue(this.permissionTypeValue);
    }

    public void setPermissionTypeValue(Integer permissionTypeValue) {
        this.permissionTypeValue = permissionTypeValue;
    }


}


