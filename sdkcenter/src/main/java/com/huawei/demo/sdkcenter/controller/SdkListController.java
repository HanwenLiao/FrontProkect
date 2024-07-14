package com.huawei.demo.sdkcenter.controller;

import com.huawei.demo.sdkcenter.entity.resp.SdkListResp;
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

    @GetMapping("/all")
    public ResultBean<List<SdkListResp>> getAllSdks() {
        List<SdkListResp> sdkList = sdkListService.getAllSdks();
        return new ResultBean<>(200, "Fetch successful", sdkList);
    }
}