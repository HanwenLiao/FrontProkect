package com.huawei.demo.sdkcenter.entity.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
public class AuditTask {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * sdk id
     */
    @TableField(value = "sdk_id")
    private Long sdkId;
    /**
     *  权限名
     */
    @TableField(value = "audit_result")
    private Integer auditResult;


    @TableField(value="audit_time")
    private Timestamp auditTime;

    @TableField(value = "audit_statement")
    private String auditStatement;

}
