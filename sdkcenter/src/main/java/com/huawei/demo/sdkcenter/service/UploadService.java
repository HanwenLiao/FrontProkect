package com.huawei.demo.sdkcenter.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huawei.demo.sdkcenter.constant.enums.Category;
import com.huawei.demo.sdkcenter.constant.enums.TaskStatus;
import com.huawei.demo.sdkcenter.entity.dao.*;
import com.huawei.demo.sdkcenter.entity.req.SdkUploadReq;
import com.huawei.demo.sdkcenter.entity.dao.mapper.*;
import com.huawei.demo.sdkcenter.util.*;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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

    @Autowired
    private DetectReportMapper detectReportMapper;

    @Value("${upload.icon.location}")
    private String ICON_LOCATION;

    @Value("${upload.har.location}")
    private String HAR_LOCATION;

    @Value("${upload.temp.location}")
    private String TEMP_LOCATION;

    private static final String ICON_FILE_REGEX = ".*\\.(png|jpg|jpeg|gif|bmp)$";
    private static final String HAR_FILE_EXTENSION = ".har";
    private static final long ICON_FILE_SIZE = 4 * 1024 * 1024;

    public void handleUploadSdk(SdkUploadReq sdkUploadReq) {
        try {
            SdkInfo sdkInfo = createSdkInfo(sdkUploadReq);
            sdkInfoMapper.insertOrUpdate(sdkInfo);
            handleAsyncDetectionTask(sdkInfo); // 调用异步方法

            new ResultBean<>(200, "Upload successful");
        } catch (IOException e) {
            log.error("Failed to handle SDK upload: {}", e.getMessage(), e);
            new ResultBean<>(500, "Upload failed: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error occurred during SDK upload: {}", e.getMessage(), e);
            new ResultBean<>(500, "Unexpected error: " + e.getMessage());
        }
    }

    @Async
    public void handleAsyncDetectionTask(SdkInfo sdkInfo) {
        try {
            SdkDetectTask sdkDetectTask = createSdkDetectTaskWithoutFiles(sdkInfo);
            generateAndSaveReport(sdkDetectTask);
        } catch (IOException e) {
            log.error("Failed to handle async detection task: {}", e.getMessage(), e);
        }
    }

    public void generateAndSaveReport(SdkDetectTask sdkDetectTask) {
        SdkInfo sdkInfo = sdkInfoMapper.queryBySHA256(sdkDetectTask.getSha256Code());
        DetectReport detectReport = createDetectReport(sdkDetectTask.getId(), sdkDetectTask, sdkInfo);
        detectReportMapper.insert(detectReport);

        // 更新任务状态
        if (detectReport.getPermissionDetected().toString().contains("isSensitive\":1")) {
            sdkDetectTask.setTaskStatus(TaskStatus.RISKY.getValue());
        } else {
            sdkDetectTask.setTaskStatus(TaskStatus.APPROVED.getValue());
        }
        sdkDetectTask.setEndTime(new Timestamp(System.currentTimeMillis()));
        sdkDetectTaskMapper.updateById(sdkDetectTask);
    }



    private SdkInfo createSdkInfo(SdkUploadReq sdkUploadReq) throws IOException {
        SdkInfo sdkInfo = new SdkInfo();
        sdkInfo.setCreatetime(new Date()); // 设置创建时间
        sdkInfo.setAuditStatus(0); // 设置默认审核状态为待审核

        if (isValidIcon(sdkUploadReq.getIcon())) {
            String iconPath = saveMultipartFile(sdkUploadReq.getIcon(), ICON_LOCATION);
            sdkInfo.setIconLocation(iconPath);
        }
        if (isValidHar(sdkUploadReq.getHar())) {
            String harPath = saveMultipartFile(sdkUploadReq.getHar(), HAR_LOCATION);
            sdkInfo.setFileLocation(harPath);
        }
        HarUtil.harFileExtraction(new File(HAR_LOCATION, Objects.requireNonNull(sdkUploadReq.getHar().getOriginalFilename())).getAbsolutePath(), TEMP_LOCATION);
        populateSdkInfo(sdkInfo, sdkUploadReq);
        return sdkInfo;
    }



    public SdkDetectTask createSdkDetectTaskWithoutFiles(SdkInfo sdkInfo) throws IOException {
        SdkDetectTask sdkDetectTask = new SdkDetectTask();
        sdkDetectTask.setStartTime(currentTimestamp());
        sdkDetectTask.setTaskStatus(TaskStatus.IN_PROGRESS.getValue());
        populateSdkDetectTaskWithoutFiles(sdkDetectTask, sdkInfo);
        sdkDetectTask.setEndTime(null); // 设置end_time为null
        sdkDetectTaskMapper.insert(sdkDetectTask); // Ensure sdkDetectTask is inserted and has an ID
        addSdkDetectTaskPermissions(sdkDetectTask); // Now call the method to add permissions
        return sdkDetectTask;
    }

    private boolean isValidIcon(MultipartFile iconFile) {
        return Optional.ofNullable(iconFile)
                .filter(file -> !file.isEmpty())
                .filter(file -> Objects.requireNonNull(file.getOriginalFilename()).toLowerCase().matches(ICON_FILE_REGEX))
                .filter(file -> file.getSize() <= ICON_FILE_SIZE)
                .isPresent();
    }

    private boolean isValidHar(MultipartFile harFile) {
        return Optional.ofNullable(harFile)
                .filter(file -> !file.isEmpty())
                .filter(file -> Objects.requireNonNull(file.getOriginalFilename()).endsWith(HAR_FILE_EXTENSION))
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
        sdkInfo.setSize(FileUtil.getFileSizeKb(new File(HAR_LOCATION, Objects.requireNonNull(sdkUploadReq.getHar().getOriginalFilename()))));
        sdkInfo.setSha256Code(FileUtil.getFileSHA256(new File(HAR_LOCATION, sdkUploadReq.getHar().getOriginalFilename())));
        sdkInfo.setUpdatetime(new Date()); // 更新 update time
    }

    private void populateSdkDetectTask(SdkDetectTask sdkDetectTask, SdkUploadReq sdkUploadReq) throws IOException {
        sdkDetectTask.setSdkName(sdkUploadReq.getSdkName());
        sdkDetectTask.setSha256Code(FileUtil.getFileSHA256(new File(HAR_LOCATION, Objects.requireNonNull(sdkUploadReq.getHar().getOriginalFilename()))));
        sdkDetectTask.setFileLocation(new File(HAR_LOCATION, sdkUploadReq.getHar().getOriginalFilename()).getAbsolutePath());
        File moduleFile = Optional.ofNullable(FileUtil.findModuleFile(new File(TEMP_LOCATION)))
                .orElseThrow(() -> new IOException("Module file not found, cannot set package name."));
        JSONObject jsonObject = ModuleUtil.jsonObjectConverter(moduleFile);
        sdkDetectTask.setPkgName(ModuleUtil.findPackageName(jsonObject));
    }

    private void populateSdkDetectTaskWithoutFiles(SdkDetectTask sdkDetectTask, SdkInfo sdkInfo) {
        sdkDetectTask.setSdkName(sdkInfo.getSdkName());
        sdkDetectTask.setSha256Code(sdkInfo.getSha256Code());
        sdkDetectTask.setFileLocation(sdkInfo.getFileLocation());
        sdkDetectTask.setPkgName(sdkInfo.getPkgName());
    }


    private void addSdkDetectTaskPermissions(SdkDetectTask sdkDetectTask) throws IOException {
        String harFilePath = sdkDetectTask.getFileLocation();
        HarUtil.harFileExtraction(harFilePath, TEMP_LOCATION);
        File moduleFile = Optional.ofNullable(FileUtil.findModuleFile(new File(TEMP_LOCATION)))
                .orElseThrow(() -> new IOException("Module file not found, cannot add permissions."));
        JSONObject jsonObject = ModuleUtil.jsonObjectConverter(moduleFile);
        List<String> permissions = ModuleUtil.findRequestPermissions(jsonObject);
        for (String permission : permissions) {
            SdkDetectTaskPermission sdkDetectTaskPermission = new SdkDetectTaskPermission();
            sdkDetectTaskPermission.setDetectTaskId(sdkDetectTask.getId());
            sdkDetectTaskPermission.setPermissionName(permission);
            sdkDetectTaskPermission.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            sdkDetectTaskPermissionMapper.insert(sdkDetectTaskPermission);
        }
    }

    private DetectReport createDetectReport(Long detectTaskId, SdkDetectTask sdkDetectTask, SdkInfo sdkInfo) {
        DetectReport detectReport = new DetectReport();
        detectReport.setDetectTaskId(detectTaskId);
        detectReport.setSdkName(sdkDetectTask.getSdkName());
        detectReport.setPkgName(sdkDetectTask.getPkgName());
        detectReport.setSha256Code(sdkDetectTask.getSha256Code());
        detectReport.setReportExportTime(new Timestamp(System.currentTimeMillis()));
        detectReport.setTaskStartTime(sdkDetectTask.getStartTime());
        detectReport.setVersionName(sdkInfo.getVersionName());
        List permissions = fetchPermissions(detectTaskId);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode permissionJsonNode = objectMapper.valueToTree(permissions);
        detectReport.setPermissionDetected(permissionJsonNode);
        return detectReport;
    }

    private List<Permission> fetchPermissions(Long detectTaskId) {
        return detectReportMapper.getPermissionsByDetectTaskId(detectTaskId);
    }

    private Timestamp currentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    private String saveMultipartFile(MultipartFile file, String location) throws IOException {
        File dir = new File(location);
        File dest = new File(dir, Objects.requireNonNull(file.getOriginalFilename()));
        file.transferTo(dest);
        return dest.getAbsolutePath();
    }

}