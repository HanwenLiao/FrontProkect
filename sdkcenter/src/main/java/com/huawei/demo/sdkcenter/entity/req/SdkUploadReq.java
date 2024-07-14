package com.huawei.demo.sdkcenter.entity.req;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

//import javax.validation.constraints.NotNull;
//import java.io.File;

/**
 * SdkInfoPojo Sdk 上传pojo
 */
@Getter
@Setter
public class SdkUploadReq {

    @Length(min = 1, max = 10240, message = "detail length must be between [1-10240]")
    //@NotNull(message = "detail must input")
    private String sdkName;

    //@NotNull(message = "detail must input")
    private String category;

    // @NotNull(message = "detail must input")
    private MultipartFile icon;

    // @NotNull(message = "detail must input")
    private MultipartFile har;

    public void setCategory(String category) {
        this.category = category;
    }

}