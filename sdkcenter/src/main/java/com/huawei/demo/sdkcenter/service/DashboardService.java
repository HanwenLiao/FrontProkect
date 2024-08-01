package com.huawei.demo.sdkcenter.service;

import com.huawei.demo.sdkcenter.constant.enums.Category;
import com.huawei.demo.sdkcenter.entity.dao.SdkInfo;
import com.huawei.demo.sdkcenter.entity.resp.SdkStatsResponse;
import com.huawei.demo.sdkcenter.entity.dao.mapper.SdkDetectTaskMapper;
import com.huawei.demo.sdkcenter.entity.dao.mapper.SdkInfoMapper;
import com.huawei.demo.sdkcenter.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private SdkInfoMapper sdkInfoMapper;

    @Autowired
    private SdkDetectTaskMapper sdkDetectTaskMapper;

    public SdkStatsResponse getSdkStats() {
        int totalSdkCount = sdkInfoMapper.selectTotalCount();
        int riskySdkCount = sdkDetectTaskMapper.selectCountByTaskStatus(2); // Assuming 2 represents 'risky' status

        SdkStatsResponse response = new SdkStatsResponse();
        response.setTotalSdkCount(totalSdkCount);
        response.setRiskySdkCount(riskySdkCount);

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
}