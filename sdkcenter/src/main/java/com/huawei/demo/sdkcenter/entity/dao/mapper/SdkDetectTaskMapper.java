package com.huawei.demo.sdkcenter.entity.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huawei.demo.sdkcenter.entity.dao.SdkDetectTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SdkDetectTaskMapper extends BaseMapper<SdkDetectTask> {

    @Select("SELECT * FROM sdk_detect_task LIMIT #{offset}, #{limit}")
    List<SdkDetectTask> selectByPage(@Param("offset") int offset, @Param("limit") int limit);

    @Select("SELECT COUNT(*) FROM sdk_detect_task")
    int selectCount();
}
