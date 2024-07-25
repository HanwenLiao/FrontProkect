package com.huawei.demo.sdkcenter.entity.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
public class SdkLatestReportResp {

    /**
     * 主键id
     */
    @JsonProperty(value = "detect_report_id")
    private String detectReportId;

    /**
     * 检测任务id
     */
    @JsonProperty(value = "detect_task_id")
    private String detectTaskId;

    /**
     * sdk名称
     */
    @JsonProperty(value = "sdk_name")
    private String sdkName;

    /**
     * sdk包名
     */
    @JsonProperty(value = "pkg_name")
    private String pkgName;

    /**
     * 版本名称
     */
    @JsonProperty(value = "version_name")
    private String versionName;

    /**
     * sha256代码
     */
    @JsonProperty(value = "sha256_code")
    private String sha256Code;

    /**
     * 检测报告输出时间
     */
    @JsonProperty(value = "report_export_time")
    private Timestamp reportExportTime;

    /**
     * 任务开始时间
     */
    @JsonProperty(value = "task_start_time")
    private Timestamp taskStartTime;

    /**
     *  权限名
     */
    @JsonProperty(value = "permission_name_list")
    private JsonNode permissionNameList;

}
