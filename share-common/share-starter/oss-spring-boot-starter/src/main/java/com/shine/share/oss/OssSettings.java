package com.shine.share.oss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * OSS对象存储 配置属性
 *
 * @author 辛凤文
 * @since 1.0
 */
@Data
@ConfigurationProperties(prefix = "oss")
public class OssSettings {
    /**
     * 访问站点
     */
    private String endpoint;

    /**
     * ACCESS_KEY
     */
    private String accessKey;

    /**
     * SECRET_KEY
     */
    private String secretKey;

    /**
     * 存储空间名
     */
    private String bucketName;

    /**
     * 存储区域
     */
    private String region;

    /**
     * 最大文件大小
     */
    private Long maxFileSize = 2L * 1024 * 1024 * 1024;
}

