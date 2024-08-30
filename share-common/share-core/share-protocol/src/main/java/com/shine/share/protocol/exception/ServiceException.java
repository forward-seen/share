package com.shine.share.protocol.exception;

import com.shine.share.protocol.constant.Error;
import com.shine.share.protocol.constant.ErrorDefinition;
import lombok.Getter;

/**
 * 服务异常
 * 因系统服务出错而发生的异常
 *
 * @author 辛凤文
 * @since 1.0
 */
@Getter
public class ServiceException extends RuntimeException {

    protected ErrorDefinition error;

    protected String desc;

    public ServiceException() {
        this.error = Error.B0001;
    }

    public ServiceException(ErrorDefinition error) {
        this.error = error;
    }

    public ServiceException(ErrorDefinition error, String desc) {
        this.error = error;
        this.desc = desc;
    }

}
