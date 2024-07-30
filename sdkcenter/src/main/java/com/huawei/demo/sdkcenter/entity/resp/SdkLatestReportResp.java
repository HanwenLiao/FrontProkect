package com.huawei.demo.sdkcenter.entity.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
public class SdkLatestReportResp {

    @JsonProperty(value = "detect_report_id")
    private String detectReportId;

    @JsonProperty(value = "detect_task_id")
    private String detectTaskId;

    @JsonProperty(value = "sdk_name")
    private String sdkName;

    @JsonProperty(value = "pkg_name")
    private String pkgName;

    @JsonProperty(value = "version_name")
    private String versionName;

    @JsonProperty(value = "sha256_code")
    private String sha256Code;

    @JsonProperty(value = "report_export_time")
    private Timestamp reportExportTime;

    @JsonProperty(value = "task_start_time")
    private Timestamp taskStartTime;

    @JsonProperty(value = "permission_name_list")
    private String permissionNameList;
}