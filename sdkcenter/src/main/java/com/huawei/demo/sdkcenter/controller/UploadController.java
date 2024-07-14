package com.huawei.demo.sdkcenter.controller;

import com.huawei.demo.sdkcenter.entity.req.SdkUploadReq;
import com.huawei.demo.sdkcenter.service.UploadService;
import com.huawei.demo.sdkcenter.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

@RestController
@CrossOrigin(maxAge = 3600)
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/api/upload")
    public ResultBean<String> upload(HttpServletRequest request,
                                     @RequestPart("sdkName") String sdkName,
                                     @RequestPart("category") String category,
                                     @RequestPart("icon") MultipartFile icon,
                                     @RequestPart("har") MultipartFile har) {
        try {
            // 设置请求的字符编码


            SdkUploadReq sdkUploadReq = new SdkUploadReq();
            sdkUploadReq.setSdkName(sdkName);
            sdkUploadReq.setCategory(new String(category.getBytes(StandardCharsets.ISO_8859_1),StandardCharsets.UTF_8));
            sdkUploadReq.setIcon(icon);
            sdkUploadReq.setHar(har);

            uploadService.handleUploadSdk(sdkUploadReq);
            return new ResultBean<>(200, "Upload successful");
        } catch (Exception e) {
            return new ResultBean<>(500, "Upload failed: " + e.getMessage());
        }
    }
}