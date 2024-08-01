package com.huawei.demo.sdkcenter.controller;

import com.huawei.demo.sdkcenter.entity.resp.SdkStatsResponse;
import com.huawei.demo.sdkcenter.service.DashboardService;
import com.huawei.demo.sdkcenter.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/api")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/sdk-stats")
    public ResultBean<SdkStatsResponse> getSdkStats() {
        SdkStatsResponse response = dashboardService.getSdkStats();
        return ResultBean.success(response);
    }

    @GetMapping("/sdk-stats/category-distribution")
    public ResultBean<Map<String, Integer>> getCategoryDistribution() {
        return dashboardService.getCategoryDistribution();
    }
}