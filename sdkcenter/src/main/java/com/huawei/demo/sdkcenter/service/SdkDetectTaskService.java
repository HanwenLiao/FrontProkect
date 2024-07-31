package com.huawei.demo.sdkcenter.service;

import com.huawei.demo.sdkcenter.entity.dao.SdkDetectTask;
import com.huawei.demo.sdkcenter.entity.dao.SdkInfo;
import com.huawei.demo.sdkcenter.entity.dao.mapper.DetectReportMapper;
import com.huawei.demo.sdkcenter.entity.dao.mapper.SdkDetectTaskMapper;
import com.huawei.demo.sdkcenter.entity.dao.mapper.SdkInfoMapper;
import com.huawei.demo.sdkcenter.entity.dao.mapper.SdkDetectTaskPermissionMapper;
import com.huawei.demo.sdkcenter.entity.resp.SdkDetectTaskHistoryResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
@CrossOrigin
@Service
@Slf4j(topic = "debug")
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

        try {
            SdkDetectTask sdkDetectTask = uploadService.createSdkDetectTaskWithoutFiles(sdkInfo);
            // 重新检测逻辑
            uploadService.generateAndSaveReport(sdkDetectTask);
        } catch (IOException e) {
            throw new RuntimeException("重新检测失败: " + e.getMessage(), e);
        }
    }
    @Scheduled(cron = "0 0 */12 * * ?")
    public void scheduleRetryDetection() {
        log.debug("Executing scheduled retry detection task");

        // 获取所有需要重新检测的SDK信息
        List<SdkInfo> sdkInfos = sdkInfoMapper.selectAll();
        for (SdkInfo sdkInfo : sdkInfos) {
            try {
                retryDetection(sdkInfo.getSha256Code());
                log.debug("Successfully retried detection for SDK with SHA256: {}", sdkInfo.getSha256Code());
            } catch (Exception e) {
                log.error("Failed to retry detection for SDK with SHA256: {}", sdkInfo.getSha256Code(), e);
            }
        }
    }
}