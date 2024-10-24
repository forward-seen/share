package com.shine.share.protocol.exception;

import com.shine.share.protocol.constant.ResultCode;
import com.shine.share.protocol.constant.Code;
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

    protected Code error;

    protected String desc;

    public BusinessException() {
        this.error = ResultCode.B0001;
    }

    public BusinessException(Code error) {
        this.error = error;
    }

    public BusinessException(Code error, String desc) {
        this.error = error;
        this.desc = desc;
    }

}
