package com.shine.share.gateway.service;

import com.shine.share.protocol.web.Result;

import java.io.IOException;

/**
 * 验证码处理
 *
 * @author 辛凤文
 * @since 1.0
 */
public interface ValidateCodeService {

    /**
     * 生成验证码
     *
     * @return
     * @throws IOException
     */
    Result<?> createCaptcha();

    /**
     * 校验验证码
     *
     * @param key
     * @param value
     */
    void checkCaptcha(String key, String value);

}
