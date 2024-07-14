package com.huawei.demo.sdkcenter.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;

import java.io.*;

/**
 * 处理HAR文件操作的工具类，包括提取和创建。
 */
@Slf4j
public class HarUtil {

    private static final int BUFFER_SIZE = 1024;

    /**
     * 将tar.gz文件解压缩到指定的输出目录。
     *
     * @param tarGzipFilePath  tar.gz文件的路径
     * @param outputDirectory  文件解压缩后的输出目录
     */
    public static void harFileExtraction(String tarGzipFilePath, String outputDirectory) {
        try (FileInputStream fis = new FileInputStream(tarGzipFilePath);
             BufferedInputStream bis = new BufferedInputStream(fis);
             GzipCompressorInputStream gzis = new GzipCompressorInputStream(bis);
             TarArchiveInputStream tarInput = new TarArchiveInputStream(gzis)) {

            ArchiveEntry entry;
            while ((entry = tarInput.getNextEntry()) != null) {
                File outputFile = new File(outputDirectory, entry.getName());
                if (entry.isDirectory()) {
                    if (!outputFile.exists() && !outputFile.mkdirs()) {
                        log.error("Failed to create directory: {}", outputFile.getAbsolutePath());
                    }
                } else {
                    if (!outputFile.getParentFile().exists() && !outputFile.getParentFile().mkdirs()) {
                        log.error("Failed to create parent directories for file: {}", outputFile.getAbsolutePath());
                    }
                    try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                        byte[] buffer = new byte[BUFFER_SIZE];
                        int bytesRead;
                        while ((bytesRead = tarInput.read(buffer)) != -1) {
                            fos.write(buffer, 0, bytesRead);
                        }
                    }
                }
            }
        } catch (IOException e) {
            log.error("Error extracting tar.gz file", e);
        }
    }

    /**
     * 从指定的源目录创建一个har文件。
     *
     * @param sourceDirectory  要归档的源目录
     * @param tarGzipFilePath  输出的tar.gz文件路径
     */
    public static void createHarFile(String sourceDirectory, String tarGzipFilePath) {
        try (FileOutputStream fos = new FileOutputStream(tarGzipFilePath);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             GzipCompressorOutputStream gzos = new GzipCompressorOutputStream(bos);
             TarArchiveOutputStream tarOutput = new TarArchiveOutputStream(gzos)) {

            File sourceDir = new File(sourceDirectory);
            addFilesToTar(tarOutput, sourceDir, "");
            tarOutput.finish();
        } catch (IOException e) {
            log.error("Error creating har file", e);
        }
    }
    private static void addFilesToTar(TarArchiveOutputStream tarOutput, File file, String parent) throws IOException {
        String entryName = parent + file.getName();
        TarArchiveEntry tarEntry = new TarArchiveEntry(file, entryName);
        tarOutput.putArchiveEntry(tarEntry);

        if (file.isFile()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    tarOutput.write(buffer, 0, bytesRead);
                }
            }
        } else {
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    addFilesToTar(tarOutput, child, entryName + "/");
                }
            }
        }
        tarOutput.closeArchiveEntry();
    }

    /**
     * 删除目录及其所有内容。
     *
     * @param directoryPath  要删除的目录路径
     */
    public static void deleteDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (directory.exists()) {
            deleteDirectory(directory);
        }
    }
    private static void deleteDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteDirectory(file);
                }
            }
        }
        if (!directory.delete()) {
            log.error("Failed to delete file or directory: {}", directory.getAbsolutePath());
        }
    }
}