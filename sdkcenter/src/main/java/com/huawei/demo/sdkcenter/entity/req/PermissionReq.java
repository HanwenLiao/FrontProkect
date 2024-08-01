package com.huawei.demo.sdkcenter.entity.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionReq {
    private String id;
    private String permissionName;
    private int isSensitive;
    private String description;
    private String permissionType;

}
