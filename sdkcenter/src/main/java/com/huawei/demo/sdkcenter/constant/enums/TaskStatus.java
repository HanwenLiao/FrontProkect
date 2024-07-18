package com.huawei.demo.sdkcenter.constant.enums;

public enum TaskStatus {
    IN_PROGRESS(0, "检测中"),
    APPROVED(1, "通过"),
    RISKY(2, "有风险"),
    REJECTED(3, "不通过"),
    FAILED(4, "任务失败");

    private final int value;
    private final String description;

    TaskStatus(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static TaskStatus fromValue(int value) {
        for (TaskStatus status : values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown enum value: " + value);
    }
}