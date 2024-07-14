package com.huawei.demo.sdkcenter.entity.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@TableName("sdk_info")
public class SdkInfo {

    @TableId(value = "sdk_id", type = IdType.ASSIGN_ID)
    private Long sdkId;

    @TableField(value = "sdk_name")
    private String sdkName;

    @TableField(value = "pkg_name")
    private String pkgName;

    @TableField(value = "version_name")
    private String versionName;

    @TableField(value = "version_code")
    private Integer versionCode;

    @TableField(value = "category")
    private Integer categoryValue;

    @TableField(value = "icon_location")
    private String iconLocation;

    @TableField(value = "size")
    private String size;

    @TableField(value = "file_location")
    private String fileLocation;

    @TableField(value = "sha256_code")
    private String sha256Code;

    @TableField(value = "updatetime")
    private Date updatetime;

    public void setCategory(int category) {
        this.categoryValue = category;
    }

    public int getCategory() {
        return this.categoryValue;
    }
}