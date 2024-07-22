package com.shine.share.oss;

import lombok.Data;

import java.io.InputStream;

/**
 * oss文件对象传输
 *
 * @author 辛凤文
 * @since 1.0
 */
@Data
public class OssFile {
    private Long contentLength;
    private String contentType;
    private InputStream inputStream;
}
