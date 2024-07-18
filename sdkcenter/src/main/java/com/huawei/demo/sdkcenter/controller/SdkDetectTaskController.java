package com.huawei.demo.sdkcenter.controller;

import com.huawei.demo.sdkcenter.entity.dao.SdkDetectTask;
import com.huawei.demo.sdkcenter.entity.resp.SdkDetectTaskHistoryResp;
import com.huawei.demo.sdkcenter.entity.resp.SdkDetectTaskResp;
import com.huawei.demo.sdkcenter.service.SdkDetectTaskService;
import com.huawei.demo.sdkcenter.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/api/detect-tasks")
public class SdkDetectTaskController {

    @Autowired
    private SdkDetectTaskService sdkDetectTaskService;

    @GetMapping("/all")
    public ResultBean<List<SdkDetectTaskResp>> getAllDetectTasks(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
        try {
            List<SdkDetectTask> sdkDetectTasks = sdkDetectTaskService.getPagedDetectTasks(page, pageSize);
            List<SdkDetectTaskResp> responseList = sdkDetectTasks.stream().map(task -> {
                SdkDetectTaskResp resp = new SdkDetectTaskResp();
                resp.setDetectTaskId(task.getId().toString()); // 转换为字符串
                resp.setSdkName(task.getSdkName());
                resp.setPkgName(task.getPkgName());
                resp.setStartTime(task.getStartTime());
                resp.setEndTime(task.getEndTime());
                return resp;
            }).collect(Collectors.toList());

            int total = sdkDetectTaskService.getTotalCount();

            return new ResultBean<>(200, "Fetch successful", responseList, total);
        } catch (Exception e) {
            return new ResultBean<>(500, "Fetch failed: " + e.getMessage());
        }
    }

    @GetMapping("/history/{sha256Code}")
    public ResultBean<List<SdkDetectTaskHistoryResp>> getDetectTaskHistory(@PathVariable String sha256Code) {
        List<SdkDetectTaskHistoryResp> historyList = sdkDetectTaskService.getDetectTaskHistoryBySha256(sha256Code);
        return new ResultBean<>(200, "Fetch successful", historyList);
    }
}