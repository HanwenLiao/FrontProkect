package com.huawei.demo.sdkcenter.entity.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huawei.demo.sdkcenter.entity.dao.DetectReport;
import com.huawei.demo.sdkcenter.entity.dao.Permission;
import com.huawei.demo.sdkcenter.entity.dao.SdkInfo;
import com.huawei.demo.sdkcenter.entity.resp.SdkLatestReportResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DetectReportMapper extends BaseMapper<DetectReport> {

    @Select("SELECT dr.*, dr.permission_detected AS permission_name_list " +
            "FROM detect_report dr " +
            "WHERE dr.detect_task_id = #{detectTaskId} " +
            "ORDER BY dr.report_export_time DESC " +
            "LIMIT 1")
    SdkLatestReportResp getLatestSdkReportByDetectTaskId(@Param("detectTaskId") Long detectTaskId);

    @Select("SELECT * FROM sdk_info WHERE pkg_name = #{pkgName} LIMIT 1")
    SdkInfo getSdkInfoByPkgName(@Param("pkgName") String pkgName);

    @Select("SELECT p.* FROM sdk_detect_task_permission dp " +
            "LEFT JOIN dict_permission p ON dp.permission_name = p.permission_name " +
            "WHERE dp.detect_task_id = #{detectTaskId}")
    List<Permission> getPermissionsByDetectTaskId(@Param("detectTaskId") Long detectTaskId);
}

