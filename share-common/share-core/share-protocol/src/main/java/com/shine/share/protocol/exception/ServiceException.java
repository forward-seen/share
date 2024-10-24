package com.shine.share.protocol.exception;

import com.shine.share.protocol.constant.ResultCode;
import com.shine.share.protocol.constant.Code;
import lombok.Getter;

/**
 * 服务异常
 * 因系统服务出错而发生的异常
 *
 * @author 辛凤文
 * @since 1.0
 */
@Getter
public class ServiceException extends Exception {

    protected Code error;

    protected String desc;

    public ServiceException() {
        this.error = ResultCode.B0001;
    }

    public ServiceException(Code error) {
        this.error = error;
    }

    public ServiceException(Code error, String desc) {
        this.error = error;
        this.desc = desc;
    }

}
