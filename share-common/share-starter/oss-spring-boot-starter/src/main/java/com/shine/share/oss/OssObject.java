package com.shine.share.oss;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * OSS对象存储 文件信息
 *
 * @author 辛凤文
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OssObject {
    private String key;
    private Long size;
    private Long lastModified;
    private Boolean isObject;

    public OssObject(S3ObjectSummary obj) {
        this.key = obj.getKey();
        this.size = obj.getSize();
        this.lastModified = obj.getLastModified().getTime();
        this.isObject = true;
    }

    public OssObject(String key) {
        this.key = key;
        this.isObject = false;
    }
}