package com.huawei.demo.sdkcenter.entity.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huawei.demo.sdkcenter.entity.dao.SdkDetectTask;
import com.huawei.demo.sdkcenter.entity.dao.SdkDetectTaskPermission;
import com.huawei.demo.sdkcenter.entity.resp.SdkDetectTaskHistoryResp;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Mapper
public interface SdkDetectTaskMapper extends BaseMapper<SdkDetectTask> {

    @Select("SELECT * FROM sdk_detect_task LIMIT #{offset}, #{limit}")
    List<SdkDetectTask> selectByPage(@Param("offset") int offset, @Param("limit") int limit);

    @Select("SELECT COUNT(*) FROM sdk_detect_task")
    int selectCount();

    @Select("SELECT task_status FROM sdk_detect_task WHERE sha256_code = #{sha256Code} ORDER BY end_time DESC LIMIT 1")
    Integer getLatestDetectStatusBySha256Code(String sha256Code);


    @Select("SELECT t.id, t.sdk_name, t.pkg_name, i.version_name, t.task_status, t.start_time, t.end_time " +
            "FROM sdk_detect_task t " +
            "JOIN sdk_info i ON t.sha256_code = i.sha256_code " +
            "WHERE t.sha256_code = #{sha256Code} " +
            "ORDER BY t.end_time DESC")
    List<SdkDetectTaskHistoryResp> getDetectTaskHistoryBySha256(@Param("sha256Code") String sha256Code);

    @Select("SELECT COUNT(*) FROM sdk_detect_task WHERE task_status = #{taskStatus}")
    int selectCountByTaskStatus(@Param("taskStatus") int taskStatus);

    @Select("SELECT * FROM sdk_detect_task WHERE sha256_code = #{sha256Code} ORDER BY end_time DESC LIMIT 1")
    SdkDetectTask getLatestTaskBySha256Code(@Param("sha256Code") String sha256Code);

    @Select("SELECT * FROM sdk_detect_task WHERE task_status = #{status} AND start_time < #{timestamp}")
    List<SdkDetectTask> findInProgressTasksStartedBefore(@Param("timestamp") Timestamp timestamp, @Param("status") int status);

}
