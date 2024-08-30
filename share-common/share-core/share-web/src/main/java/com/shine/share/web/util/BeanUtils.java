package com.shine.share.web.util;

/**
 * Bean转换、处理工具类
 *
 * @author 辛凤文
 * @since 1.0
 */
public class BeanUtils {

    public static <T> T convert(Object sourceBean, Class<T> targetClass) {
        if (sourceBean == null || targetClass == null) {
            return null;
        }
        try {
            T instance = targetClass.getDeclaredConstructor().newInstance();
            org.springframework.beans.BeanUtils.copyProperties(sourceBean, instance);
            return instance;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将source的属性值赋给target
     */
    public static void copy(Object source, Object target) {
        if (source == null || target == null) {
            return;
        }
        org.springframework.beans.BeanUtils.copyProperties(source, target);
    }

}
