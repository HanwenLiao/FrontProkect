package com.huawei.demo.sdkcenter.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huawei.demo.sdkcenter.entity.dao.SdkDetectTask;
import com.huawei.demo.sdkcenter.entity.dao.mapper.SdkDetectTaskMapper;
import com.huawei.demo.sdkcenter.entity.resp.SdkDetectTaskHistoryResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SdkDetectTaskService {

    @Autowired
    private SdkDetectTaskMapper sdkDetectTaskMapper;

    public List<SdkDetectTask> getPagedDetectTasks(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return sdkDetectTaskMapper.selectByPage(offset, pageSize);
    }

    public int getTotalCount() {
        return sdkDetectTaskMapper.selectCount();
    }


    public List<SdkDetectTaskHistoryResp> getDetectTaskHistoryBySha256(String sha256Code) {
        return sdkDetectTaskMapper.getDetectTaskHistoryBySha256(sha256Code);
    }
}