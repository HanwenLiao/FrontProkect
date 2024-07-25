package com.huawei.demo.sdkcenter.entity.resp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionResp {
    private Long id;
    private String permissionName;
    private Integer isSensitive;
    private String description;
    private String permissionType;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public Integer getIsSensitive() {
        return isSensitive;
    }

    public void setIsSensitive(Integer isSensitive) {
        this.isSensitive = isSensitive;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(String permissionType) {
        this.permissionType = permissionType;
    }

}