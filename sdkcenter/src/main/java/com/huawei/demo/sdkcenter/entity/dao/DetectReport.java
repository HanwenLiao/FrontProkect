package com.huawei.demo.sdkcenter.entity.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@TableName("detect_report")
public class DetectReport {

    @TableId(value = "detect_report_id", type = IdType.ASSIGN_ID)
    private Long detectReportId;

    @TableField(value = "detect_task_id")
    private long detectTaskId;

    @TableField(value = "sdk_name")
    private String sdkName;

    @TableField(value = "pkg_name")
    private String pkgName;

    @TableField(value = "version_name")
    private String versionName;

    @TableField(value = "sha256_code")
    private String sha256Code;

    @TableField(value = "report_export_time")
    private Timestamp reportExportTime;

    @TableField(value = "task_start_time")
    private Timestamp taskStartTime;

    @JsonIgnore
    @TableField(value = "permission_detected")
    private String permissionDetectedString;

    @TableField(exist = false)
    private JsonNode permissionDetected;

    public void setPermissionDetected(JsonNode permissionDetected) {
        this.permissionDetected = permissionDetected;
        this.permissionDetectedString = permissionDetected != null ? permissionDetected.toString() : null;
    }

    public JsonNode getPermissionDetected() {
        if (this.permissionDetected == null && this.permissionDetectedString != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                this.permissionDetected = mapper.readTree(this.permissionDetectedString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this.permissionDetected;
    }
}