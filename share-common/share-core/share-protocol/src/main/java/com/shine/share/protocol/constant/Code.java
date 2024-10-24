package com.shine.share.protocol.constant;

import java.io.Serializable;

/**
 * 编码定义接口
 * 编码约定相关枚举类实现该接口
 *
 * @author 辛凤文
 * @since 1.0
 */
public interface Code extends Serializable {

    String getCode();

    String getDesc();

}
