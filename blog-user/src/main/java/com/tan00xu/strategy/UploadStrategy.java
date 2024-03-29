package com.tan00xu.strategy;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;


/**
 * 上传策略类
 *
 * @author 饮梦 TAN00XU
 * @date 2022/10/26 14:32:10
 */
public interface UploadStrategy {

    /**
     * 上传文件
     *
     * @param file 文件
     * @param path 上传路径
     * @return {@link String} 文件地址
     */
    String uploadFile(MultipartFile file, String path);

    /**
     * 上传文件
     *
     * @param fileName    文件名
     * @param inputStream 输入流
     * @param path        路径
     * @return {@link String}
     */
    String uploadFile(String fileName, InputStream inputStream, String path);

}
