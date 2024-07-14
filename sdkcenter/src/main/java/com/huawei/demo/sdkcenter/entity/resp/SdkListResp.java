package com.huawei.demo.sdkcenter.entity.resp;



import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;
@Setter
@Getter
public class SdkListResp {
    private String sdkId;

    private String sdkName;

    private String pkgName;

    private String versionName;

    private Integer versionCode;

    private String categoryValue;

    private String iconLocation;

    private String size;

    private String sha256Code;

    private Timestamp updatetime;
}





