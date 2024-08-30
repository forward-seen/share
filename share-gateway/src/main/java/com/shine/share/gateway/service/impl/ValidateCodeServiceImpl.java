package com.shine.share.gateway.service.impl;

import com.google.code.kaptcha.Producer;
import com.shine.share.gateway.config.properties.CaptchaProperties;
import com.shine.share.gateway.constant.ErrorCode;
import com.shine.share.gateway.service.ValidateCodeService;
import com.shine.share.protocol.exception.ServiceException;
import com.shine.share.protocol.web.Result;
import com.shine.share.redis.constant.CacheConstants;
import com.shine.share.redis.manager.RedisManager;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 验证码实现处理
 *
 * @author 辛凤文
 * @since 1.0
 */
@Slf4j
@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {

    @Resource
    private Producer captchaProducer;

    @Resource
    private Producer captchaProducerMath;

    @Resource
    private RedisManager redisManager;

    @Resource
    private CaptchaProperties captchaProperties;

    @SneakyThrows
    @Override
    public Result<?> createCaptcha() {
        Map<String, Object> data = new HashMap<>();
        Result<Map<String, Object>> result = new Result<>(data);
        boolean captchaEnabled = captchaProperties.getEnabled();
        data.put("captchaEnabled", captchaEnabled);
        if (!captchaEnabled) {
            return result;
        }

        // 保存验证码信息
        String uuid = UUID.randomUUID().toString();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;

        String capStr, code;
        BufferedImage image;

        String captchaType = captchaProperties.getType();
        // 生成验证码
        if ("math".equals(captchaType)) {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        } else if ("char".equals(captchaType)) {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        } else {
            log.error("验证码配置异常，请检查配置security.captcha.type，支持math/char选项，不支持{}", captchaType);
            throw new ServiceException(ErrorCode.B0240);
        }

        redisManager.setCacheObject(verifyKey, code, captchaProperties.getExpiration(), TimeUnit.MINUTES);

        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        ImageIO.write(image, "jpg", os);
        data.put("expiration", captchaProperties.getExpiration());
        data.put("uuid", uuid);
        data.put("img", Base64.getEncoder().encodeToString(os.toByteArray()));
        return result;
    }

    @Override
    public void checkCaptcha(String code, String uuid) {
        if (code == null || code.isBlank()) {
            throw new ServiceException(ErrorCode.A0240);
        }
        if (uuid == null || uuid.isBlank()) {
            throw new ServiceException(ErrorCode.A0241);
        }
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisManager.getCacheObject(verifyKey);
        if (captcha == null) {
            throw new ServiceException(ErrorCode.A0241);
        }
        redisManager.deleteObject(verifyKey);

        if (!code.equalsIgnoreCase(captcha)) {
            throw new ServiceException(ErrorCode.A0242);
        }
    }

}
