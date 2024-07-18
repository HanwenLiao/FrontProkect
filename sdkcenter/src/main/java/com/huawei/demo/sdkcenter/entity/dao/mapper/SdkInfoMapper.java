package com.huawei.demo.sdkcenter.entity.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huawei.demo.sdkcenter.entity.dao.SdkInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SdkInfoMapper extends BaseMapper<SdkInfo> {
    @Insert("INSERT INTO sdk_info (id, sdk_name, pkg_name, version_name, version_code, category, icon_location, size, file_location, sha256_code, updatetime, createtime, audit_status) " +
            "VALUES (#{sdkInfo.id}, #{sdkInfo.sdkName}, #{sdkInfo.pkgName}, #{sdkInfo.versionName}, #{sdkInfo.versionCode}, #{sdkInfo.categoryValue}, #{sdkInfo.iconLocation}, #{sdkInfo.size}, #{sdkInfo.fileLocation}, #{sdkInfo.sha256Code}, #{sdkInfo.updatetime}, #{sdkInfo.createtime}, #{sdkInfo.auditStatus}) " +
            "ON DUPLICATE KEY UPDATE " +
            "sdk_name = VALUES(sdk_name), " +
            "version_code = VALUES(version_code), " +
            "category = VALUES(category), " +
            "icon_location = VALUES(icon_location), " +
            "size = VALUES(size), " +
            "file_location = VALUES(file_location), " +
            "sha256_code = VALUES(sha256_code), " +
            "updatetime = VALUES(updatetime), " +
            "audit_status = VALUES(audit_status)")
    void insertOrUpdate(@Param("sdkInfo") SdkInfo sdkInfo);

    @Select("SELECT * " +
            "FROM sdk_info si " +
            "WHERE si.sha256_code = #{sha256_code}")
    SdkInfo queryBySHA256(@Param("sha256_code") String sha256_code);
}