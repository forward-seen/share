package com.shine.share.oss;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * OSS自动配置类
 *
 * @author 辛凤文
 * @since 1.0
 */
@EnableConfigurationProperties(OssSettings.class)
@ConditionalOnProperty(prefix = "oss", name = "enabled", havingValue = "true", matchIfMissing = true)
@AutoConfiguration
public class OssAutoConfiguration {

    private final OssSettings ossSettings;

    public OssAutoConfiguration(OssSettings ossSettings) {
        this.ossSettings = ossSettings;
    }

    @Bean
    public OssClient ossClient() {
        return new OssClient(ossSettings);
    }

}
