package com.shine.share.gateway.config;

import com.google.code.kaptcha.text.impl.DefaultTextCreator;

import java.util.Random;

/**
 * 验证码文本生成器
 *
 * @author 辛凤文
 * @since 1.0
 */
public class CaptchaTextCreator extends DefaultTextCreator {

    private static final String[] NUMBERS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    @Override
    public String getText() {
        int result;
        Random random = new Random();
        int x = random.nextInt(10);
        int y = random.nextInt(10);
        StringBuilder suChinese = new StringBuilder();
        int randomOperands = random.nextInt(3);
        if (randomOperands == 0) {
            result = x * y;
            suChinese.append(NUMBERS[x]);
            suChinese.append("*");
            suChinese.append(NUMBERS[y]);
        } else if (randomOperands == 1) {
            if ((x != 0) && y % x == 0) {
                result = y / x;
                suChinese.append(NUMBERS[y]);
                suChinese.append("/");
                suChinese.append(NUMBERS[x]);
            } else {
                result = x + y;
                suChinese.append(NUMBERS[x]);
                suChinese.append("+");
                suChinese.append(NUMBERS[y]);
            }
        } else {
            if (x >= y) {
                result = x - y;
                suChinese.append(NUMBERS[x]);
                suChinese.append("-");
                suChinese.append(NUMBERS[y]);
            } else {
                result = y - x;
                suChinese.append(NUMBERS[y]);
                suChinese.append("-");
                suChinese.append(NUMBERS[x]);
            }
        }
        suChinese.append("=?@").append(result);
        return suChinese.toString();
    }
}