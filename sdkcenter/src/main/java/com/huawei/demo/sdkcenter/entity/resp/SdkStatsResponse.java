package com.huawei.demo.sdkcenter.entity.resp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SdkStatsResponse {
    private int totalSdkCount;
    private int riskySdkCount;
    private int approvedSdkCount;
    private int rejectedSdkCount;
}