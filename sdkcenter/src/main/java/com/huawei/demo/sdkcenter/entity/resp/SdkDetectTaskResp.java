package com.huawei.demo.sdkcenter.entity.resp;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class SdkDetectTaskResp {
    private String detectTaskId; // 修改为字符串类型
    private String sdkName;
    private String pkgName;
    private Timestamp startTime;
    private Timestamp endTime;
}