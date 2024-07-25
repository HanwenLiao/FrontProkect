package com.huawei.demo.sdkcenter.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huawei.demo.sdkcenter.constant.enums.TaskStatus;
import com.huawei.demo.sdkcenter.entity.dao.SdkDetectTask;
import com.huawei.demo.sdkcenter.entity.dao.SdkInfo;
import com.huawei.demo.sdkcenter.entity.dao.mapper.DetectReportMapper;
import com.huawei.demo.sdkcenter.entity.dao.mapper.SdkDetectTaskMapper;
import com.huawei.demo.sdkcenter.entity.dao.mapper.SdkInfoMapper;
import com.huawei.demo.sdkcenter.entity.dao.mapper.SdkDetectTaskPermissionMapper;
import com.huawei.demo.sdkcenter.entity.resp.SdkDetectTaskHistoryResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class SdkDetectTaskService {

    @Autowired
    private SdkDetectTaskMapper sdkDetectTaskMapper;
    @Autowired
    private SdkInfoMapper sdkInfoMapper;


    @Autowired
    private SdkDetectTaskPermissionMapper sdkDetectTaskPermissionMapper;

    @Autowired
    private DetectReportMapper detectReportMapper;

    @Autowired
    private UploadService uploadService;

    public List<SdkDetectTask> getPagedDetectTasks(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return sdkDetectTaskMapper.selectByPage(offset, pageSize);
    }

    public int getTotalCount() {
        return sdkDetectTaskMapper.selectCount();
    }


    public List<SdkDetectTaskHistoryResp> getDetectTaskHistoryBySha256(String sha256Code) {
        return sdkDetectTaskMapper.getDetectTaskHistoryBySha256(sha256Code);
    }



    public void retryDetection(String sha256Code) {
        // 根据sha256Code查找相关的SdkInfo和SdkDetectTask
        SdkInfo sdkInfo = sdkInfoMapper.queryBySHA256(sha256Code);
        if (sdkInfo == null) {
            throw new RuntimeException("未找到相关的SDK信息");
        }

        SdkDetectTask sdkDetectTask = new SdkDetectTask();
        sdkDetectTask.setSdkName(sdkInfo.getSdkName());
        sdkDetectTask.setPkgName(sdkInfo.getPkgName());
        sdkDetectTask.setSha256Code(sha256Code);
        sdkDetectTask.setFileLocation(sdkInfo.getFileLocation());
        sdkDetectTask.setStartTime(new Timestamp(System.currentTimeMillis()));
        sdkDetectTask.setEndTime(null); // 设置end_time为null
        sdkDetectTask.setTaskStatus(TaskStatus.IN_PROGRESS.getValue());

        sdkDetectTaskMapper.insert(sdkDetectTask);

        // 重新检测逻辑
        uploadService.generateAndSaveReport(sdkDetectTask);

        sdkDetectTask.setEndTime(new Timestamp(System.currentTimeMillis()));
        sdkDetectTaskMapper.updateById(sdkDetectTask);
    }
}