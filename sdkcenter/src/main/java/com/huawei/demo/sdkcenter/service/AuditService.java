package com.huawei.demo.sdkcenter.service;

import com.huawei.demo.sdkcenter.entity.dao.AuditTask;
import com.huawei.demo.sdkcenter.entity.dao.SdkInfo;
import com.huawei.demo.sdkcenter.entity.dao.mapper.AuditTaskMapper;
import com.huawei.demo.sdkcenter.entity.dao.mapper.SdkInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class AuditService {
    @Autowired
    private SdkInfoMapper sdkInfoMapper;

    @Autowired
    private AuditTaskMapper auditTaskMapper;

    public int updateAuditStatus(String sha256Code, Integer auditStatus) {
        return sdkInfoMapper.updateAuditStatus(sha256Code, auditStatus);
    }

    public void createAuditTask(Integer auditStatus, String statement, String sha256Code) {
        AuditTask auditTask = new AuditTask();
        populateAuditStatusBySdkInfo(auditTask, sha256Code);
        auditTask.setAuditResult(auditStatus);
        auditTask.setAuditTime(new Timestamp(System.currentTimeMillis()));
        auditTask.setAuditStatement(statement);
        auditTaskMapper.insert(auditTask);
    }

    private void populateAuditStatusBySdkInfo(AuditTask auditTask, String sha256Code) {
        SdkInfo sdkInfo = sdkInfoMapper.queryBySHA256(sha256Code);
        auditTask.setSdkId(sdkInfo.getId());
    }
}