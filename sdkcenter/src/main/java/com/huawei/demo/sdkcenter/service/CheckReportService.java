//
//package com.huawei.demo.sdkcenter.service;
//
//import com.huawei.demo.sdkcenter.entity.resp.SdkLatestReportResp;
//import com.huawei.demo.sdkcenter.entity.dao.mapper.DetectReportMapper;
//import com.huawei.demo.sdkcenter.util.ResultBean;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//@Slf4j(topic = "debug")
//public class CheckReportService {
//    @Autowired
//    private DetectReportMapper detectReportMapper;
//
//    /**
//     * 从数据库中获取最新的报告。
//     *
//     * @param detectTaskId 检测任务的ID
//     * @return 最新的 SDK 报告
//     */
//    public ResultBean<SdkLatestReportResp> fetchLatestReport(Long detectTaskId) {
//        try {
//            log.debug("Fetching the latest report for detectTaskId: {}", detectTaskId);
//
//            SdkLatestReportResp report = fetchLatestSdkReport(detectTaskId);
//            log.debug("Successfully fetched latest report for detectTaskId: {}", detectTaskId);
//            return new ResultBean<>(200, "Success", report);
//        } catch (Exception e) {
//            log.error("Failed to fetch the latest report: {}", e.getMessage(), e);
//            return new ResultBean<>(500, "Failed to fetch the latest report");
//        }
//    }
//
//    private SdkLatestReportResp fetchLatestSdkReport(Long detectTaskId) {
//        return Optional.ofNullable(detectReportMapper.getLatestSdkReportByDetectTaskId(detectTaskId))
//                .orElseThrow(() -> {
//                    log.error("No report found for detectTaskId: {}", detectTaskId);
//                    return new IllegalArgumentException("No report found for detectTaskId: " + detectTaskId);
//                });
//    }
//
//
//}
package com.huawei.demo.sdkcenter.service;

import com.huawei.demo.sdkcenter.entity.resp.SdkLatestReportResp;
import com.huawei.demo.sdkcenter.entity.dao.DetectReport;
import com.huawei.demo.sdkcenter.entity.dao.mapper.DetectReportMapper;
import com.huawei.demo.sdkcenter.util.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j(topic = "debug")
public class CheckReportService {

    @Autowired
    private DetectReportMapper detectReportMapper;

    public ResultBean<SdkLatestReportResp> fetchLatestReport(Long detectTaskId) {
        try {
            log.debug("Fetching the latest report for detectTaskId: {}", detectTaskId);

            DetectReport detectReport = fetchLatestDetectReport(detectTaskId);
            if (detectReport != null) {
                SdkLatestReportResp report = convertToSdkLatestReportResp(detectReport);
                log.debug("Successfully fetched latest report for detectTaskId: {}", detectTaskId);
                return new ResultBean<>(200, "Success", report);
            } else {
                log.error("No report found for detectTaskId: {}", detectTaskId);
                return new ResultBean<>(500, "No report found");
            }
        } catch (Exception e) {
            log.error("Failed to fetch the latest report: {}", e.getMessage(), e);
            return new ResultBean<>(500, "Failed to fetch the latest report");
        }
    }

    private DetectReport fetchLatestDetectReport(Long detectTaskId) {
        return Optional.ofNullable(detectReportMapper.getLatestDetectReportByDetectTaskId(detectTaskId))
                .orElseThrow(() -> {
                    log.error("No report found for detectTaskId: {}", detectTaskId);
                    return new IllegalArgumentException("No report found for detectTaskId: " + detectTaskId);
                });
    }

    private SdkLatestReportResp convertToSdkLatestReportResp(DetectReport detectReport) {
        SdkLatestReportResp report = new SdkLatestReportResp();
        report.setDetectReportId(detectReport.getId().toString());
        report.setDetectTaskId(Long.toString(detectReport.getDetectTaskId()));
        report.setSdkName(detectReport.getSdkName());
        report.setPkgName(detectReport.getPkgName());
        report.setVersionName(detectReport.getVersionName());
        report.setSha256Code(detectReport.getSha256Code());
        report.setReportExportTime(detectReport.getReportExportTime());
        report.setTaskStartTime(detectReport.getTaskStartTime());
        report.setPermissionNameList(detectReport.getPermissionDetectedString());

        return report;
    }
}