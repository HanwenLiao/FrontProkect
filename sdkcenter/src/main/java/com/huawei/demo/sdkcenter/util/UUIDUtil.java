package com.huawei.demo.sdkcenter.util;

import java.util.UUID;

public class UUIDUtil {

    /**
     * Generates a unique identifier using UUID
     *
     * @return a unique identifier as a String
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }
}
