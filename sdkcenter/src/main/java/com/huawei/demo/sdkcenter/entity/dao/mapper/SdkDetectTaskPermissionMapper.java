package com.huawei.demo.sdkcenter.entity.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huawei.demo.sdkcenter.entity.dao.SdkDetectTaskPermission;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SdkDetectTaskPermissionMapper extends BaseMapper<SdkDetectTaskPermission> {
    @Insert({
            "<script>",
            "INSERT INTO sdk_detect_task_permission (detect_task_id, permission_name, updatetime) VALUES ",
            "<foreach collection='permissions' item='permission' separator=','>",
            "(#{permission.detectTaskId}, #{permission.permissionName}, #{permission.updateTime})",
            "</foreach>",
            "</script>"
    })
    int insertBatch(@Param("permissions") List<SdkDetectTaskPermission> permissions);
}
