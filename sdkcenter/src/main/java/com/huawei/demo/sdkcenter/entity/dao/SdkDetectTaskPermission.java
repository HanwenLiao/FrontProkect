package com.huawei.demo.sdkcenter.entity.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@TableName("sdk_detect_task_permission")
public class SdkDetectTaskPermission {
    /**
     * 主键id
     */
    @TableId(value = "detect_task_permission_id", type = IdType.ASSIGN_ID)
    private Long detectTaskPermissionId;
    /**
     *  权限名
     */
    @TableField(value = "permission_name")
    private String permissionName;
    /**
     * 关联任务id
     */
    @TableField(value = "detect_task_id")
    private Long detectTaskId;

    @TableField(value="updatetime")
    private Timestamp updateTime;
}

