package com.huawei.demo.sdkcenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
@MapperScan("com.huawei.demo.sdkcenter.entity.dao.mapper")
public class SdkCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SdkCenterApplication.class, args);
    }

}
