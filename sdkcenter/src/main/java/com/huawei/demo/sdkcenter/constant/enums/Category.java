package com.huawei.demo.sdkcenter.constant.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public enum Category {
    LIFE_SERVICE(1, "生活服务"),
    SECURITY(2, "安全"),
    MEDIA(3, "媒体"),
    AI(4, "AI"),
    PLATFORM_SERVICE(5, "平台服务"),
    AD(6, "广告"),
    PAYMENT(7, "支付"),
    ANALYTICS(8, "分析"),
    SOCIAL(9, "社交"),
    FINANCE(10, "金融理财"),
    TOOLS(11, "工具"),
    ACCOUNT_LOGIN(12, "账号登录"),
    GAME(13, "游戏"),
    FRAMEWORK(14, "框架"),
    PERFORMANCE_MONITORING(15, "性能监控"),
    NETWORK(16, "网络"),
    PUSH(17, "推送"),
    MAP_LOCATION(18, "地图定位"),
    STORAGE(19, "存储"),
    ECOMMERCE(20, "电商服务"),
    AR_VR(21, "AR/VR"),
    DEEP_CHAIN(22, "深链"),
    OTHER(23, "其他");

    private static final Logger logger = LoggerFactory.getLogger(Category.class);
    private final int value;
    private final String description;

    Category(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static Category fromValue(int value) {
        for (Category type : values()) {
            if (type.value == value) {
                return type;
            }
        }
        logger.error("Unknown enum value: {}", value);
        return OTHER; // 返回一个默认值
    }

    public static Category fromDescription(String description) {
        for (Category type : values()) {
            if (type.description.equals(new String(description.getBytes(), StandardCharsets.UTF_8))) {
                return type;
            }
        }
        logger.error("Unknown enum description: {}", description);
        return OTHER; // 返回一个默认值
    }
}