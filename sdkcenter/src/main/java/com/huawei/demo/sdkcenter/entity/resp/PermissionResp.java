package com.huawei.demo.sdkcenter.entity.resp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionResp {
    private String id;
    private String permissionName;
    private Integer isSensitive;
    private String description;
    private String permissionType;

}