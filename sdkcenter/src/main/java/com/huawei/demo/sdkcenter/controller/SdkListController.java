package com.huawei.demo.sdkcenter.controller;

import com.huawei.demo.sdkcenter.entity.resp.SdkListResp;
import com.huawei.demo.sdkcenter.service.AuditService;
import com.huawei.demo.sdkcenter.service.SdkListService;
import com.huawei.demo.sdkcenter.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/api/sdk-list")
public class SdkListController {

    @Autowired
    private SdkListService sdkListService;

    @Autowired
    private AuditService auditService;

    @PostMapping("/update-audit-status")
    public ResultBean<String> updateAuditStatus(@RequestParam String sha256Code, @RequestParam Integer auditStatus, @RequestParam String auditStatement) {
        auditService.createAuditTask(auditStatus,auditStatement,sha256Code);
        int result = auditService.updateAuditStatus(sha256Code, auditStatus);
        if (result > 0) {
            return new ResultBean<>(200, "Audit status updated successfully");
        } else {
            return new ResultBean<>(500, "Failed to update audit status");
        }
    }

    @GetMapping("/all")
    public ResultBean<List<SdkListResp>> getAllSdks(@RequestParam int page, @RequestParam int size) {
        List<SdkListResp> sdkList = sdkListService.getAllSdks(page, size);
        int total = sdkListService.getTotalCount();
        return new ResultBean<>(200, "Fetch successful", sdkList, total);
    }
}