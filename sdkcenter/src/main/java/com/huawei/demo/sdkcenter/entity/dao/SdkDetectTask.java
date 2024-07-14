package com.huawei.demo.sdkcenter.entity.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * SdkDetectTask entity class
 *
 * @since 2024-06-03
 */
@Getter
@Setter
@TableName("sdk_detect_task")
public class SdkDetectTask {

    /**
     * 主键id
     */
    @TableId(value = "detect_task_id", type = IdType.ASSIGN_ID)
    private Long detectTaskId;

    /**
     * sdk名称
     */
    @TableField(value = "sdk_name")
    private String sdkName;

    /**
     * sdk包名
     */
    @TableField(value = "pkg_name")
    private String pkgName;

    /**
     * sdk文件位置
     */
    @TableField(value = "file_location")
    private String fileLocation;

    /**
     * sha256Code
     */
    @TableField(value = "sha256_code")
    private String sha256Code;

    /**
     * 开始时间
     */
    @TableField(value = "start_time")
    private Timestamp startTime;

    /**
     * 结束时间
     */
    @TableField(value = "end_time")
    private Timestamp endTime;
}