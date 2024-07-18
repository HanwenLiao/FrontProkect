package com.huawei.demo.sdkcenter.service;

import com.huawei.demo.sdkcenter.entity.dao.SdkInfo;
import com.huawei.demo.sdkcenter.entity.resp.SdkListResp;
import com.huawei.demo.sdkcenter.entity.dao.mapper.SdkInfoMapper;
import com.huawei.demo.sdkcenter.entity.dao.mapper.SdkDetectTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SdkListService {

    @Autowired
    private SdkInfoMapper sdkInfoMapper;

    @Autowired
    private SdkDetectTaskMapper sdkDetectTaskMapper;

    public List<SdkListResp> getAllSdks() {
        List<SdkInfo> sdkInfos = sdkInfoMapper.selectList(null);
        return sdkInfos.stream().map(sdk -> {
            SdkListResp resp = new SdkListResp();
            resp.setSdkName(sdk.getSdkName());
            resp.setPkgName(sdk.getPkgName());
            resp.setVersionName(sdk.getVersionName());
            resp.setCategoryValue(sdk.getCategoryValue().toString());
            resp.setIconLocation(sdk.getIconLocation());
            resp.setSize(sdk.getSize());
            resp.setUpdatetime(new Timestamp(sdk.getUpdatetime().getTime()));
            resp.setAuditStatus(sdk.getAuditStatus());

            // 获取最新的检测任务状态
            Integer latestDetectStatus = sdkDetectTaskMapper.getLatestDetectStatusBySha256Code(sdk.getSha256Code());
            resp.setDetectStatus(latestDetectStatus);

            return resp;
        }).collect(Collectors.toList());
    }
}