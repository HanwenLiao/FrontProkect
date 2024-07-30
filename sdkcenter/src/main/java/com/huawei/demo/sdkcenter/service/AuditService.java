package com.huawei.demo.sdkcenter.service;

import com.huawei.demo.sdkcenter.entity.dao.mapper.SdkInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditService {
    @Autowired
    private SdkInfoMapper sdkInfoMapper;

    public int updateAuditStatus(String sha256Code, Integer auditStatus) {
        return sdkInfoMapper.updateAuditStatus(sha256Code, auditStatus);
    }
}