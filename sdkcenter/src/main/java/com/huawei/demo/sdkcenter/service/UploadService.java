package com.huawei.demo.sdkcenter.service;

import com.huawei.demo.sdkcenter.constant.enums.Category;
import com.huawei.demo.sdkcenter.entity.dao.SdkDetectTask;
import com.huawei.demo.sdkcenter.entity.dao.SdkDetectTaskPermission;
import com.huawei.demo.sdkcenter.entity.dao.SdkInfo;
import com.huawei.demo.sdkcenter.entity.req.SdkUploadReq;
import com.huawei.demo.sdkcenter.entity.dao.mapper.SdkDetectTaskMapper;
import com.huawei.demo.sdkcenter.entity.dao.mapper.SdkDetectTaskPermissionMapper;
import com.huawei.demo.sdkcenter.entity.dao.mapper.SdkInfoMapper;
import com.huawei.demo.sdkcenter.util.FileUtil;
import com.huawei.demo.sdkcenter.util.HarUtil;
import com.huawei.demo.sdkcenter.util.ModuleUtil;
import com.huawei.demo.sdkcenter.util.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j(topic = "debug")
public class UploadService {

    @Autowired
    private SdkInfoMapper sdkInfoMapper;

    @Autowired
    private SdkDetectTaskMapper sdkDetectTaskMapper;

    @Autowired
    private SdkDetectTaskPermissionMapper sdkDetectTaskPermissionMapper;

    private static final String ICON_LOCATION = "/Users/liaohanwen/Documents/workspace/sdkcenter/src/main/resources/static/upload/icon";
    private static final String HAR_LOCATION = "/Users/liaohanwen/Documents/workspace/sdkcenter/src/main/resources/static/upload/har";
    private static final String TEMP_LOCATION = "/Users/liaohanwen/Documents/workspace/sdkcenter/src/upload/temp";
    private static final long ICON_FILE_SIZE = 4 * 1024 * 1024;
    private static final String ICON_FILE_REGEX = ".*\\.(png|jpg|jpeg|gif|bmp)$";
    private static final String HAR_FILE_EXTENSION = ".har";

    public ResultBean<String> handleUploadSdk(SdkUploadReq sdkUploadReq) {
        try {
            SdkInfo sdkInfo = createSdkInfo(sdkUploadReq);
            SdkDetectTask sdkDetectTask = createSdkDetectTask(sdkUploadReq);
            sdkInfo.setUpdatetime(new Date());
            sdkInfoMapper.insertOrUpdate(sdkInfo);
            sdkDetectTaskMapper.insert(sdkDetectTask);
            addSdkDetectTaskPermissions(sdkDetectTask);
            HarUtil.deleteDirectory(TEMP_LOCATION);
            return new ResultBean<>(200, "Upload successful");
        } catch (IOException e) {
            log.error("Failed to handle SDK upload: {}", e.getMessage(), e);
            return new ResultBean<>(500, "Upload failed: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error occurred during SDK upload: {}", e.getMessage(), e);
            return new ResultBean<>(500, "Unexpected error: " + e.getMessage());
        }
    }

    private SdkInfo createSdkInfo(SdkUploadReq sdkUploadReq) throws IOException {
        SdkInfo sdkInfo = new SdkInfo();
        if (isValidIcon(sdkUploadReq.getIcon())) {
            String iconPath = saveMultipartFile(sdkUploadReq.getIcon(), ICON_LOCATION);
            sdkInfo.setIconLocation(iconPath);
        }
        if (isValidHar(sdkUploadReq.getHar())) {
            String harPath = saveMultipartFile(sdkUploadReq.getHar(), HAR_LOCATION);
            sdkInfo.setFileLocation(harPath);
        }
        HarUtil.harFileExtraction(new File(HAR_LOCATION, sdkUploadReq.getHar().getOriginalFilename()).getAbsolutePath(), TEMP_LOCATION);
        populateSdkInfo(sdkInfo, sdkUploadReq);
        return sdkInfo;
    }

    private SdkDetectTask createSdkDetectTask(SdkUploadReq sdkUploadReq) throws IOException {
        SdkDetectTask sdkDetectTask = new SdkDetectTask();
        sdkDetectTask.setStartTime(currentTimestamp());
        populateSdkDetectTask(sdkDetectTask, sdkUploadReq);
        sdkDetectTask.setEndTime(currentTimestamp());
        return sdkDetectTask;
    }

    private boolean isValidIcon(MultipartFile iconFile) {
        return Optional.ofNullable(iconFile)
                .filter(file -> !file.isEmpty())
                .filter(file -> file.getOriginalFilename().toLowerCase().matches(ICON_FILE_REGEX))
                .filter(file -> file.getSize() <= ICON_FILE_SIZE)
                .isPresent();
    }

    private boolean isValidHar(MultipartFile harFile) {
        return Optional.ofNullable(harFile)
                .filter(file -> !file.isEmpty())
                .filter(file -> file.getOriginalFilename().endsWith(HAR_FILE_EXTENSION))
                .isPresent();
    }

    private void populateSdkInfo(SdkInfo sdkInfo, SdkUploadReq sdkUploadReq) throws IOException {
        sdkInfo.setSdkName(sdkUploadReq.getSdkName());
        sdkInfo.setCategory(Category.fromDescription(new String(sdkUploadReq.getCategory().getBytes(), StandardCharsets.UTF_8)).getValue());

        File moduleFile = Optional.ofNullable(FileUtil.findModuleFile(new File(TEMP_LOCATION)))
                .orElseThrow(() -> new IOException("Module file not found, cannot set package name, version name, or version code."));
        JSONObject jsonObject = ModuleUtil.jsonObjectConverter(moduleFile);
        sdkInfo.setPkgName(ModuleUtil.findPackageName(jsonObject));
        sdkInfo.setVersionName(ModuleUtil.findVersionName(jsonObject));
        sdkInfo.setVersionCode(ModuleUtil.findVersionCode(jsonObject));
        sdkInfo.setSize(FileUtil.getFileSizeKb(new File(HAR_LOCATION, sdkUploadReq.getHar().getOriginalFilename())));
        sdkInfo.setSha256Code(FileUtil.getFileSHA256(new File(HAR_LOCATION, sdkUploadReq.getHar().getOriginalFilename())));
    }

    private void populateSdkDetectTask(SdkDetectTask sdkDetectTask, SdkUploadReq sdkUploadReq) throws IOException {
        sdkDetectTask.setSdkName(sdkUploadReq.getSdkName());
        sdkDetectTask.setSha256Code(FileUtil.getFileSHA256(new File(HAR_LOCATION, sdkUploadReq.getHar().getOriginalFilename())));
        sdkDetectTask.setFileLocation(new File(HAR_LOCATION, sdkUploadReq.getHar().getOriginalFilename()).getAbsolutePath());
        File moduleFile = Optional.ofNullable(FileUtil.findModuleFile(new File(TEMP_LOCATION)))
                .orElseThrow(() -> new IOException("Module file not found, cannot set package name."));
        JSONObject jsonObject = ModuleUtil.jsonObjectConverter(moduleFile);
        sdkDetectTask.setPkgName(ModuleUtil.findPackageName(jsonObject));
    }

    private void addSdkDetectTaskPermissions(SdkDetectTask sdkDetectTask) throws IOException {
        File moduleFile = Optional.ofNullable(FileUtil.findModuleFile(new File(TEMP_LOCATION)))
                .orElseThrow(() -> new IOException("Module file not found, cannot add permissions."));
        JSONObject jsonObject = ModuleUtil.jsonObjectConverter(moduleFile);
        List<String> permissions = ModuleUtil.findRequestPermissions(jsonObject);
        for (String permission : permissions) {
            SdkDetectTaskPermission sdkDetectTaskPermission = new SdkDetectTaskPermission();
            sdkDetectTaskPermission.setDetectTaskId(sdkDetectTask.getDetectTaskId());
            sdkDetectTaskPermission.setPermissionName(permission);
            sdkDetectTaskPermission.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            sdkDetectTaskPermissionMapper.insert(sdkDetectTaskPermission);
        }
    }

    private Timestamp currentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    private String saveMultipartFile(MultipartFile file, String location) throws IOException {
        File dir = new File(location);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File dest = new File(dir, file.getOriginalFilename());
        file.transferTo(dest);
        return dest.getAbsolutePath();
    }
}