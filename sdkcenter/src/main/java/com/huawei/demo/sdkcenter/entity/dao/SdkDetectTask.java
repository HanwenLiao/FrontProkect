package com.huawei.demo.sdkcenter.entity.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@TableName("sdk_detect_task")
public class SdkDetectTask {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(value = "sdk_name")
    private String sdkName;

    @TableField(value = "pkg_name")
    private String pkgName;

    @TableField(value = "file_location")
    private String fileLocation;

    @TableField(value = "sha256_code")
    private String sha256Code;

    @TableField(value = "start_time")
    private Timestamp startTime;

    @TableField(value = "end_time")
    private Timestamp endTime;

    @TableField(value = "task_status")
    private Integer taskStatus;
}