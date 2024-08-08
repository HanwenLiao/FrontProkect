package com.huawei.demo.sdkcenter.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huawei.demo.sdkcenter.constant.enums.Category;
import com.huawei.demo.sdkcenter.entity.dao.Permission;
import com.huawei.demo.sdkcenter.entity.dao.SdkDetectTask;
import com.huawei.demo.sdkcenter.entity.dao.SdkDetectTaskPermission;
import com.huawei.demo.sdkcenter.entity.dao.SdkInfo;
import com.huawei.demo.sdkcenter.entity.dao.mapper.PermissionMapper;
import com.huawei.demo.sdkcenter.entity.dao.mapper.SdkDetectTaskPermissionMapper;
import com.huawei.demo.sdkcenter.entity.resp.SensitiveDataItem;
import com.huawei.demo.sdkcenter.entity.resp.SdkStatsResponse;
import com.huawei.demo.sdkcenter.entity.dao.mapper.SdkDetectTaskMapper;
import com.huawei.demo.sdkcenter.entity.dao.mapper.SdkInfoMapper;
import com.huawei.demo.sdkcenter.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class DashboardService {

    @Autowired
    private SdkInfoMapper sdkInfoMapper;

    @Autowired
    private SdkDetectTaskMapper sdkDetectTaskMapper;

    @Autowired
    private SdkDetectTaskPermissionMapper sdkDetectTaskPermissionMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    public SdkStatsResponse getSdkStats() {
        int totalSdkCount = sdkInfoMapper.selectTotalCount();
        int riskySdkCount = sdkDetectTaskMapper.selectCountByTaskStatus(2); // Assuming 2 represents 'risky' status
        int approvedSdkCount = Math.toIntExact(sdkInfoMapper.selectCount(new QueryWrapper<SdkInfo>().eq("audit_status", 1)));
        int rejectedSdkCount = Math.toIntExact(sdkInfoMapper.selectCount(new QueryWrapper<SdkInfo>().eq("audit_status", 2)));

        SdkStatsResponse response = new SdkStatsResponse();
        response.setTotalSdkCount(totalSdkCount);
        response.setRiskySdkCount(riskySdkCount);
        response.setApprovedSdkCount(approvedSdkCount);
        response.setRejectedSdkCount(rejectedSdkCount);

        return response;
    }

    public ResultBean<Map<String, Integer>> getCategoryDistribution() {
        List<SdkInfo> sdkInfoList = sdkInfoMapper.selectList(null);
        Map<String, Integer> categoryCount = new HashMap<>();

        for (SdkInfo sdkInfo : sdkInfoList) {
            String category = Category.fromValue(sdkInfo.getCategoryValue()).getDescription();
            categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
        }

        return ResultBean.success(categoryCount);
    }

    public ResultBean<Map<String, Map<String, Integer>>> getHeatmapData(boolean sensitiveOnly) {
        List<SdkInfo> sdkInfoList = sdkInfoMapper.selectList(null);
        Map<String, Map<String, Integer>> heatmapData = new HashMap<>();

        for (SdkInfo sdkInfo : sdkInfoList) {
            String category = Category.fromValue(sdkInfo.getCategoryValue()).getDescription();

            SdkDetectTask latestTask = sdkDetectTaskMapper.getLatestTaskBySha256Code(sdkInfo.getSha256Code());
            if (latestTask != null) {
                List<SdkDetectTaskPermission> permissions = sdkDetectTaskPermissionMapper.selectList(
                        new QueryWrapper<SdkDetectTaskPermission>().eq("detect_task_id", latestTask.getId()));

                for (SdkDetectTaskPermission taskPermission : permissions) {
                    Permission permission = permissionMapper.selectOne(
                            new QueryWrapper<Permission>().eq("permission_name", taskPermission.getPermissionName()));
                    if (permission != null && (!sensitiveOnly || (sensitiveOnly && permission.getIsSensitive() == 1))) {
                        String permissionType = permission.getPermissionType().getDescription();
                        heatmapData
                                .computeIfAbsent(category, k -> new HashMap<>())
                                .merge(permissionType, 1, Integer::sum);
                    }
                }
            }
        }

        return ResultBean.success(heatmapData);
    }

    public ResultBean<Map<String, Integer>> getPermissionDistribution(int categoryValue) {
        Map<String, Integer> permissionDistribution = new HashMap<>();

        List<SdkInfo> sdkInfoList = sdkInfoMapper.selectList(
                new QueryWrapper<SdkInfo>().eq("category", categoryValue));
        for (SdkInfo sdkInfo : sdkInfoList) {
            SdkDetectTask latestTask = sdkDetectTaskMapper.getLatestTaskBySha256Code(sdkInfo.getSha256Code());
            if (latestTask != null) {
                List<SdkDetectTaskPermission> permissions = sdkDetectTaskPermissionMapper.selectList(
                        new QueryWrapper<SdkDetectTaskPermission>().eq("detect_task_id", latestTask.getId()));
                for (SdkDetectTaskPermission taskPermission : permissions) {
                    Permission permission = permissionMapper.selectOne(
                            new QueryWrapper<Permission>().eq("permission_name", taskPermission.getPermissionName()));
                    if (permission != null && permission.getIsSensitive() == 1) {
                        String permissionType = permission.getPermissionType().getDescription();
                        permissionDistribution.merge(permissionType, 1, Integer::sum);
                    }
                }
            }
        }

        return ResultBean.success(permissionDistribution);
    }

    public ResultBean<Map<String, Object>> getSensitiveData() {
        List<SdkInfo> sdkInfoList = sdkInfoMapper.selectList(null);
        Map<String, Object> result = new HashMap<>();
        Map<String, Integer> chartData = new HashMap<>();
        List<SensitiveDataItem> tableData = new ArrayList<>();

        for (SdkInfo sdkInfo : sdkInfoList) {
            String category = Category.fromValue(sdkInfo.getCategoryValue()).getDescription();
            int total = 0;
            int risky = 0;
            String mostSensitivePermissionType = null;
            Map<String, Integer> permissionCounts = new HashMap<>();

            SdkDetectTask latestTask = sdkDetectTaskMapper.getLatestTaskBySha256Code(sdkInfo.getSha256Code());
            if (latestTask != null) {
                List<SdkDetectTaskPermission> permissions = sdkDetectTaskPermissionMapper.selectList(
                        new QueryWrapper<SdkDetectTaskPermission>().eq("detect_task_id", latestTask.getId()));

                for (SdkDetectTaskPermission taskPermission : permissions) {
                    Permission permission = permissionMapper.selectOne(
                            new QueryWrapper<Permission>().eq("permission_name", taskPermission.getPermissionName()));
                    if (permission != null) {
                        total++;
                        if (permission.getIsSensitive() == 1) {
                            risky++;
                            permissionCounts.merge(permission.getPermissionType().getDescription(), 1, Integer::sum);
                        }
                    }
                }
            }

            if (!permissionCounts.isEmpty()) {
                mostSensitivePermissionType = permissionCounts.entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .map(Map.Entry::getKey)
                        .orElse(null);
            }

            tableData.add(new SensitiveDataItem(category, total, risky, String.format("%.2f%%", risky * 100.0 / total), mostSensitivePermissionType));
        }

        result.put("chartData", chartData);
        result.put("tableData", tableData);

        return ResultBean.success(result);
    }

}