package com.shine.share.protocol.exception;

import com.shine.share.protocol.constant.ErrorCode;

/**
 * 服务异常
 * 因系统服务出错诱发的异常
 *
 * @author 辛凤文
 * @since 1.0
 */
public class ServiceException extends RuntimeException {

    protected ErrorCode errorCode;

    protected String desc;

    public ServiceException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ServiceException(ErrorCode errorCode, String desc) {
        this.errorCode = errorCode;
        this.desc = desc;
    }

    public ServiceException() {
        this.errorCode = ErrorCode.B0001;
    }

}
