package com.shine.share.protocol.exception;

import com.shine.share.protocol.constant.Error;
import com.shine.share.protocol.constant.ErrorDefinition;
import lombok.Getter;

/**
 * 业务异常
 * 因业务逻辑错误而发生的异常
 *
 * @author 辛凤文
 * @since 1.0
 */
@Getter
public class BusinessException extends RuntimeException {

    protected ErrorDefinition error;

    protected String desc;

    public BusinessException() {
        this.error = Error.B0001;
    }

    public BusinessException(ErrorDefinition error) {
        this.error = error;
    }

    public BusinessException(ErrorDefinition error, String desc) {
        this.error = error;
        this.desc = desc;
    }

}
