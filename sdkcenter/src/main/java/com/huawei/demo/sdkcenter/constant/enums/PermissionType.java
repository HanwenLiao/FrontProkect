package com.huawei.demo.sdkcenter.constant.enums;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public enum PermissionType {
    BLUETOOTH(1, "蓝牙"),
    NETWORK(2, "网络"),
    AUDIO(3, "音频"),
    NOTIFICATION(4, "通知"),
    TELEPHONE(5, "电话"),
    ABILITY(6, "能力"),
    STORAGE(7, "存储"),
    SYSTEM(8, "系统"),
    DOWNLOAD(9, "下载"),
    EVENT(10, "事件"),
    WINDOW(11, "窗口"),
    HARDWARE(12, "硬件"),
    INPUT(13, "输入"),
    DISPLAY(14, "显示"),
    BROADCAST(15, "广播"),
    PRIVACY(16, "隐私"),
    MEDIA(17, "媒体"),
    SECURITY(18, "安全"),
    HEALTH(19, "健康"),
    LOCATION(20, "位置"),
    CAMERA(21, "相机"),
    CALENDAR(22, "日历"),
    ACCOUNT(23, "账户"),
    ACCESSIBILITY(24, "无障碍"),
    MICROPHONE(25, "麦克风"),
    CONTACTS(26, "联系人"),
    SENSOR(27, "传感器"),
    NFC(28, "NFC"),
    FITNESS(29, "健身运动"),
    DISTRIBUTED_DATA_MANAGEMENT(30, "分布式数据管理");

    private static final Logger logger = LoggerFactory.getLogger(PermissionType.class);

    private final int value;
    private final String description;

    PermissionType(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public static PermissionType fromValue(Integer value) {
        if (value == null) {
            return null; // 或者返回一个默认值，比如 TYPE_0
        }
        for (PermissionType type : PermissionType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown enum value: " + value);
    }
}