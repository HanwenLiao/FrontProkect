package com.huawei.demo.sdkcenter.entity.resp;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class SdkListResp {
    private String sdkName;
    private String pkgName;
    private String versionName;
    private String categoryValue;
    private String iconLocation;
    private String size;
    private Timestamp updatetime;
    private Integer auditStatus;
    private Integer detectStatus;
}