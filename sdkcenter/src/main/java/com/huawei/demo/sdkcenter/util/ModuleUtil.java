package com.huawei.demo.sdkcenter.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Module工具类
 */
public class ModuleUtil {

    /**
     * 将文件转换为JSONObject
     *
     * @param file 要转换的文件
     * @return 转换后的JSONObject
     */
    public static JSONObject jsonObjectConverter(File file) {
        JSONObject jsonObject = null;
        try {
            // 读取文件内容为字符串
            String content = new String(Files.readAllBytes(file.toPath()));
            // 将字符串内容转换为JSONObject
            jsonObject = new JSONObject(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * 从JSONObject中查找请求的权限
     *
     * @param jsonObject 包含权限的JSONObject
     * @return 请求的权限列表
     */
    public static List<String> findRequestPermissions(JSONObject jsonObject) {
        List<String> permissions = new ArrayList<>();
        try {
            if (!jsonObject.getJSONObject("module").isEmpty()) {
                JSONObject moduleObject = jsonObject.getJSONObject("module");
                if (!moduleObject.getJSONArray("requestPermissions").isEmpty()) {
                    JSONArray requestPermissionsArray = moduleObject.getJSONArray("requestPermissions");
                    for (int i = 0; i < requestPermissionsArray.length(); i++) {
                        permissions.add(String.valueOf(requestPermissionsArray.getJSONObject(i).get("name")));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return permissions;
    }

    /**
     * 从JSONObject中查找版本名称
     *
     * @param jsonObject 包含版本名称的JSONObject
     * @return 版本名称
     */
    public static String findVersionName(JSONObject jsonObject) {
        try {
            if (!jsonObject.getJSONObject("app").isEmpty()) {
                JSONObject appObject = jsonObject.getJSONObject("app");
                return (String) appObject.get("versionName");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从JSONObject中查找包名称
     *
     * @param jsonObject 包含包名称的JSONObject
     * @return 包名称
     */
    public static String findPackageName(JSONObject jsonObject) {
        try {
            if (!jsonObject.getJSONObject("app").isEmpty()) {
                JSONObject appObject = jsonObject.getJSONObject("app");
                return (String) appObject.get("bundleName");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从JSONObject中查找版本代码
     *
     * @param jsonObject 包含版本代码的JSONObject
     * @return 版本代码
     */
    public static int findVersionCode(JSONObject jsonObject) {
        try {
            if (!jsonObject.getJSONObject("app").isEmpty()) {
                JSONObject appObject = jsonObject.getJSONObject("app");
                return (int) appObject.get("versionCode");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

//    public static void main(String[] args) {
//        File file = new File("src/upload/myfile/module.json");
//        JSONObject jsonObject = jsonObjectConverter(file);
//        // System.out.println(jsonObject);
//        System.out.println("SDK 包名称: " + findPackageName(jsonObject));
//        System.out.println("SDK 版本名称: " + findVersionName(jsonObject));
//        System.out.println("SDK 版本代码: " + findVersionCode(jsonObject));
//        System.out.println("SDK 使用以下权限: " + findRequestPermissions(jsonObject));
//    }

}