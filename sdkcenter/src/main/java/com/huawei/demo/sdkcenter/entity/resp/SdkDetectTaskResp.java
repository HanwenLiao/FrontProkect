package com.huawei.demo.sdkcenter.entity.resp;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class SdkDetectTaskResp {
    private String detectTaskId; // 修改为字符串类型
    private String sdkName;
    private String pkgName;
    private Timestamp startTime;
    private Timestamp endTime;
}