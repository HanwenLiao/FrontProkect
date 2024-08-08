package com.huawei.demo.sdkcenter.entity.resp;

import lombok.Data;

@Data
public class SensitiveDataItem {
    private String category;
    private int total;
    private int risky;
    private String percentage;
    private String mostSensitivePermission;

    public SensitiveDataItem(String category, int total, int risky, String percentage, String mostSensitivePermission) {
        this.category = category;
        this.total = total;
        this.risky = risky;
        this.percentage = percentage;
        this.mostSensitivePermission = mostSensitivePermission;
    }
}