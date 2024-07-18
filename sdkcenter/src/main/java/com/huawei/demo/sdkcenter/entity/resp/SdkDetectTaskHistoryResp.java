package com.huawei.demo.sdkcenter.entity.resp;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class SdkDetectTaskHistoryResp {
    private String id;
    private String sdkName;
    private String pkgName;
    private String versionName;
    private int taskStatus;
    private Timestamp startTime;
    private Timestamp endTime;
}