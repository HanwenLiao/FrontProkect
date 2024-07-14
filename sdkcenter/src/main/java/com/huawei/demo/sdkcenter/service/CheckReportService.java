package com.huawei.demo.sdkcenter.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huawei.demo.sdkcenter.entity.dao.DetectReport;
import com.huawei.demo.sdkcenter.entity.dao.Permission;
import com.huawei.demo.sdkcenter.entity.dao.SdkDetectTask;
import com.huawei.demo.sdkcenter.entity.dao.SdkInfo;
import com.huawei.demo.sdkcenter.entity.resp.SdkLatestResp;
import com.huawei.demo.sdkcenter.entity.dao.mapper.DetectReportMapper;
import com.huawei.demo.sdkcenter.entity.dao.mapper.SdkDetectTaskMapper;
import com.huawei.demo.sdkcenter.util.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j(topic = "debug")
public class CheckReportService {

    @Autowired
    private DetectReportMapper detectReportMapper;

    @Autowired
    private SdkDetectTaskMapper sdkDetectTaskMapper;

    /**
     * 为给定的 detectTaskId 生成并获取最新的报告。
     *
     * @param detectTaskId 检测任务的ID
     * @return 最新的 SDK 报告
     */
    public ResultBean<SdkLatestResp> generateAndFetchLatestReport(Long detectTaskId) {
        try {
            log.debug("Generating and fetching the latest report for detectTaskId: {}", detectTaskId);

            SdkDetectTask sdkDetectTask = getSdkDetectTask(detectTaskId);
            SdkInfo sdkInfo = getSdkInfo(sdkDetectTask.getPkgName());

            DetectReport detectReport = createDetectReport(detectTaskId, sdkDetectTask, sdkInfo);
            detectReportMapper.insert(detectReport);

            SdkLatestResp report = fetchLatestSdkReport(detectTaskId);
            report.setPermissionNameList(detectReport.getPermissionDetected());
            log.debug("Successfully generated latest report for detectTaskId: {}", detectTaskId);
            return new ResultBean<>(200, "Success", report);
        } catch (Exception e) {
            log.error("Failed to generate and fetch the latest report: {}", e.getMessage(), e);
            return new ResultBean<>(500, "Failed to generate and fetch the latest report");
        }
    }

    private SdkDetectTask getSdkDetectTask(Long detectTaskId) {
        return Optional.ofNullable(sdkDetectTaskMapper.selectById(detectTaskId))
                .orElseThrow(() -> {
                    log.error("Invalid detectTaskId: {}", detectTaskId);
                    return new IllegalArgumentException("Invalid detectTaskId: " + detectTaskId);
                });
    }

    private SdkInfo getSdkInfo(String pkgName) {
        return Optional.ofNullable(detectReportMapper.getSdkInfoByPkgName(pkgName))
                .orElseThrow(() -> {
                    log.error("No SdkInfo found for pkgName: {}", pkgName);
                    return new IllegalArgumentException("No SdkInfo found for pkgName: " + pkgName);
                });
    }

    private DetectReport createDetectReport(Long detectTaskId, SdkDetectTask sdkDetectTask, SdkInfo sdkInfo) {
        DetectReport detectReport = new DetectReport();
        detectReport.setDetectTaskId(detectTaskId);
        detectReport.setSdkName(sdkDetectTask.getSdkName());
        detectReport.setPkgName(sdkDetectTask.getPkgName());
        detectReport.setSha256Code(sdkDetectTask.getSha256Code());
        detectReport.setReportExportTime(new Timestamp(System.currentTimeMillis()));
        detectReport.setTaskStartTime(sdkDetectTask.getStartTime());
        detectReport.setVersionName(sdkInfo.getVersionName());

        List<Permission> permissions = fetchPermissions(detectTaskId);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode permissionJsonNode = objectMapper.valueToTree(permissions);
        detectReport.setPermissionDetected(permissionJsonNode);

        return detectReport;
    }

    private SdkLatestResp fetchLatestSdkReport(Long detectTaskId) {
        return Optional.ofNullable(detectReportMapper.getLatestSdkReportByDetectTaskId(detectTaskId))
                .orElseThrow(() -> {
                    log.error("No report found for detectTaskId: {}", detectTaskId);
                    return new IllegalArgumentException("No report found for detectTaskId: " + detectTaskId);
                });
    }

    private List<Permission> fetchPermissions(Long detectTaskId) {
        return detectReportMapper.getPermissionsByDetectTaskId(detectTaskId);
    }
}