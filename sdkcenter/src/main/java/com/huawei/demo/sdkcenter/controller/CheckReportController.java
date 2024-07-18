package com.huawei.demo.sdkcenter.controller;

import com.huawei.demo.sdkcenter.entity.resp.SdkLatestReportResp;
import com.huawei.demo.sdkcenter.service.CheckReportService;
import com.huawei.demo.sdkcenter.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@RestController
public class CheckReportController {

    @Autowired
    private CheckReportService checkReportService;

    @GetMapping("/checkreport")
    public ResultBean<SdkLatestReportResp> getReport(@RequestParam long detectTaskId) {
        return checkReportService.fetchLatestReport(detectTaskId);
    }
}