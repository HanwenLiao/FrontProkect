package com.huawei.demo.sdkcenter.constant.enums;

public enum AuditStatus {
    PENDING(0, "待审核"),
    APPROVED(1, "通过"),
    REJECTED(2, "不通过");

    private final int value;
    private final String description;

    AuditStatus(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static AuditStatus fromValue(int value) {
        for (AuditStatus status : values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown enum value: " + value);
    }
}