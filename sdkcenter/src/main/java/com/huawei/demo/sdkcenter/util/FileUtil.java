//package com.huawei.demo.sdkcenter.util;
//
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.Objects;
//
///**
// * File utility class
// */
//@Slf4j
//public class FileUtil {
//
//    private static final String SHA_256 = "SHA-256";
//    private static final int BUFFER_SIZE = 1024;
//    private static final String MODULE_JSON = "module.json";
//    private static final String SIZE_UNIT = "KB";
//    private static final String ERROR_SHA256 = "Error calculating SHA256 hash: ";
//    private static final String ERROR_FILE_SIZE = "Error calculating file size: ";
//    private static final String ERROR_FIND_MODULE = "Error finding module file: ";
//    private static final String ERROR_SAVE_FILE = "Error saving file: ";
//
//    /**
//     * 获取文件 sha256
//     *
//     * @param file 文件
//     * @return sha256
//     */
//    public static String getFileSHA256(File file) {
//        try (InputStream inputStream = Files.newInputStream(file.toPath())) {
//            MessageDigest digest = MessageDigest.getInstance(SHA_256);
//            byte[] buffer = new byte[BUFFER_SIZE];
//            int read;
//            while ((read = inputStream.read(buffer)) != -1) {
//                digest.update(buffer, 0, read);
//            }
//            return bytesToHex(digest.digest());
//        } catch (NoSuchAlgorithmException | IOException e) {
//            log.error(ERROR_SHA256, e);
//            return null;
//        }
//    }
//
//    private static String bytesToHex(byte[] hashBytes) {
//        StringBuilder hashString = new StringBuilder();
//        for (byte b : hashBytes) {
//            String hex = Integer.toHexString(0xff & b);
//            if (hex.length() == 1) {
//                hashString.append('0');
//            }
//            hashString.append(hex);
//        }
//        return hashString.toString();
//    }
//
//    /**
//     * 获取文件大小（Kb）
//     *
//     * @param file 文件
//     * @return file size
//     */
//    public static String getFileSizeKb(File file) {
//        try {
//            if (file.exists()) {
//                double fileSizeKb = (double) file.length() / 1024;
//                return Math.round(fileSizeKb) + SIZE_UNIT;
//            }
//        } catch (Exception e) {
//            log.error(ERROR_FILE_SIZE, e);
//        }
//        return null;
//    }
//
//    /**
//     * 找到module文件路径
//     *
//     * @param directory 目录
//     * @return module文件
//     * @throws IOException
//     */
//    public static File findModuleFile(File directory) throws IOException {
//        try {
//            if (directory.isDirectory()) {
//                for (File file : Objects.requireNonNull(directory.listFiles())) {
//                    if (file.isFile() && file.getName().equals(MODULE_JSON)) {
//                        return file;
//                    } else if (file.isDirectory()) {
//                        File result = findModuleFile(file);
//                        if (result != null) {
//                            return result;
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            log.error(ERROR_FIND_MODULE, e);
//            throw e;
//        }
//        return null;
//    }
//
//    /**
//     * 保存文件至指定路径
//     *
//     * @param inputFile      文件
//     * @param destinationDir 目标路径
//     * @return 文件路径
//     */
//    public static String saveFileLocal(File inputFile, String destinationDir) {
//        try {
//            ensureDirectoryExists(destinationDir);
//            java.nio.file.Path destination = Paths.get(destinationDir).resolve(UUIDUtil.getUUID() + "_" + inputFile.getName());
//            Files.copy(inputFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
//            return destination.toString();
//        } catch (IOException e) {
//            log.error(ERROR_SAVE_FILE, e);
//            return null;
//        }
//    }
//
//    private static void ensureDirectoryExists(String destinationDir) throws IOException {
//        java.nio.file.Path destinationDirectory = Paths.get(destinationDir);
//        if (!Files.exists(destinationDirectory)) {
//            Files.createDirectories(destinationDirectory);
//        }
//    }
//
////    public static void main(String[] args) throws IOException {
////        File a = new File("D:\\workspace\\demo0617\\src\\sdk.har");
////        System.out.println(saveFileLocal(a, "D:\\workspace\\demo0617\\src\\upload\\har"));
////    }
//}
package com.huawei.demo.sdkcenter.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Optional;

/**
 * File utility class
 */
@Slf4j
public class FileUtil {

    private static final String SHA_256 = "SHA-256";
    private static final int BUFFER_SIZE = 1024;
    private static final String MODULE_JSON = "module.json";
    private static final String SIZE_UNIT = "KB";
    private static final String ERROR_SHA256 = "Error calculating SHA256 hash: ";
    private static final String ERROR_FILE_SIZE = "Error calculating file size: ";
    private static final String ERROR_FIND_MODULE = "Error finding module file: ";
    private static final String ERROR_SAVE_FILE = "Error saving file: ";

    /**
     * 获取文件 sha256
     *
     * @param file 文件
     * @return sha256
     */
    public static String getFileSHA256(File file) {
        try (InputStream inputStream = Files.newInputStream(file.toPath())) {
            MessageDigest digest = MessageDigest.getInstance(SHA_256);
            byte[] buffer = new byte[BUFFER_SIZE];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                digest.update(buffer, 0, read);
            }
            return bytesToHex(digest.digest());
        } catch (NoSuchAlgorithmException | IOException e) {
            log.error(ERROR_SHA256, e);
            return null;
        }
    }

    private static String bytesToHex(byte[] hashBytes) {
        StringBuilder hashString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hashString.append('0');
            }
            hashString.append(hex);
        }
        return hashString.toString();
    }

    /**
     * 获取文件大小（Kb）
     *
     * @param file 文件
     * @return file size
     */
    public static String getFileSizeKb(File file) {
        try {
            return Optional.ofNullable(file)
                    .filter(File::exists)
                    .map(f -> (double) f.length() / 1024)
                    .map(size -> Math.round(size) + SIZE_UNIT)
                    .orElse(null);
        } catch (Exception e) {
            log.error(ERROR_FILE_SIZE, e);
            return null;
        }
    }

    /**
     * 找到module文件路径
     *
     * @param directory 目录
     * @return module文件
     * @throws IOException
     */
    public static File findModuleFile(File directory) throws IOException {
        try {
            return Optional.ofNullable(directory)
                    .filter(File::isDirectory)
                    .map(File::listFiles)
                    .map(files -> {
                        for (File file : files) {
                            if (file.isFile() && MODULE_JSON.equals(file.getName())) {
                                return file;
                            } else if (file.isDirectory()) {
                                try {
                                    File result = findModuleFile(file);
                                    if (result != null) {
                                        return result;
                                    }
                                } catch (IOException e) {
                                    log.error(ERROR_FIND_MODULE, e);
                                }
                            }
                        }
                        return null;
                    })
                    .orElse(null);
        } catch (Exception e) {
            log.error(ERROR_FIND_MODULE, e);
            throw e;
        }
    }

    /**
     * 保存文件至指定路径
     *
     * @param inputFile      文件
     * @param destinationDir 目标路径
     * @return 文件路径
     */
    public static String saveFileLocal(File inputFile, String destinationDir) {
        try {
            ensureDirectoryExists(destinationDir);
            java.nio.file.Path destination = Paths.get(destinationDir).resolve(UUIDUtil.getUUID() + "_" + inputFile.getName());
            Files.copy(inputFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
            return destination.toString();
        } catch (IOException e) {
            log.error(ERROR_SAVE_FILE, e);
            return null;
        }
    }

    private static void ensureDirectoryExists(String destinationDir) throws IOException {
        java.nio.file.Path destinationDirectory = Paths.get(destinationDir);
        if (!Files.exists(destinationDirectory)) {
            Files.createDirectories(destinationDirectory);
        }
    }
}