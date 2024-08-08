package com.huawei.demo.sdkcenter.controller;

import com.huawei.demo.sdkcenter.entity.resp.SdkStatsResponse;
import com.huawei.demo.sdkcenter.service.DashboardService;
import com.huawei.demo.sdkcenter.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
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

    @GetMapping("/sdk-stats/heatmap")
    public ResultBean<Map<String, Map<String, Integer>>> getHeatmapData(@RequestParam(value = "sensitiveOnly", defaultValue = "false") boolean sensitiveOnly) {
        return dashboardService.getHeatmapData(sensitiveOnly);
    }

    @GetMapping("/sdk-stats/permission-distribution")
    public ResultBean<Map<String, Integer>> getPermissionDistribution(@RequestParam int category) {
        return dashboardService.getPermissionDistribution(category);
    }

    @GetMapping("/sdk-stats/sensitive-data")
    public ResultBean<Map<String, Object>> getSensitiveData() {
        return dashboardService.getSensitiveData();
    }
}