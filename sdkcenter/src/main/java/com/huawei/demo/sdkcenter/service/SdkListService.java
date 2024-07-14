package com.huawei.demo.sdkcenter.service;

import com.huawei.demo.sdkcenter.entity.dao.SdkInfo;
import com.huawei.demo.sdkcenter.entity.resp.SdkListResp;
import com.huawei.demo.sdkcenter.entity.dao.mapper.SdkInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SdkListService {

    @Autowired
    private SdkInfoMapper sdkInfoMapper;

    public List<SdkListResp> getAllSdks() {
        List<SdkInfo> sdkInfos = sdkInfoMapper.selectList(null);
        return sdkInfos.stream().map(sdk -> {
            SdkListResp resp = new SdkListResp();
            resp.setSdkId(sdk.getSdkId().toString());
            resp.setSdkName(sdk.getSdkName());
            resp.setPkgName(sdk.getPkgName());
            resp.setVersionName(sdk.getVersionName());
            resp.setVersionCode(sdk.getVersionCode());
            resp.setCategoryValue(sdk.getCategoryValue().toString());
            resp.setIconLocation(sdk.getIconLocation());
            resp.setSize(sdk.getSize());
            resp.setSha256Code(sdk.getSha256Code());
            resp.setUpdatetime(new Timestamp(sdk.getUpdatetime().getTime()));
            return resp;
        }).collect(Collectors.toList());
    }
}