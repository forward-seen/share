package com.shine.share.protocol.exception;

import com.shine.share.protocol.constant.ErrorCode;

/**
 * 业务异常
 * 因业务逻辑错误而诱发的异常
 *
 * @author 辛凤文
 * @since 1.0
 */
public class BusinessException extends RuntimeException {

    protected ErrorCode errorCode;

    protected String desc;

    public BusinessException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode, String desc) {
        this.errorCode = errorCode;
        this.desc = desc;
    }

    public BusinessException() {
        this.errorCode = ErrorCode.B0001;
    }
}
