package com.huawei.demo.sdkcenter.entity.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huawei.demo.sdkcenter.entity.dao.SdkInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface SdkInfoMapper extends BaseMapper<SdkInfo> {
    @Insert("INSERT INTO sdk_info (sdk_id, sdk_name, pkg_name, version_name, version_code, category, icon_location, size, file_location, sha256_code, updatetime) " +
            "VALUES (#{sdkInfo.sdkId}, #{sdkInfo.sdkName}, #{sdkInfo.pkgName}, #{sdkInfo.versionName}, #{sdkInfo.versionCode}, #{sdkInfo.categoryValue}, #{sdkInfo.iconLocation}, #{sdkInfo.size}, #{sdkInfo.fileLocation}, #{sdkInfo.sha256Code}, #{sdkInfo.updatetime}) " +
            "ON DUPLICATE KEY UPDATE " +
            "sdk_name = VALUES(sdk_name), " +
            "version_code = VALUES(version_code), " +
            "category = VALUES(category), " +
            "icon_location = VALUES(icon_location), " +
            "size = VALUES(size), " +
            "file_location = VALUES(file_location), " +
            "sha256_code = VALUES(sha256_code), " +
            "updatetime = VALUES(updatetime)")
    void insertOrUpdate(@Param("sdkInfo") SdkInfo sdkInfo);
}