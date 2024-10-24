package com.shine.share.protocol.helper;

import com.shine.share.protocol.constant.ResultCode;
import com.shine.share.protocol.exception.BusinessException;
import com.shine.share.protocol.exception.ServiceException;
import com.shine.share.protocol.domain.Result;

/**
 * 将抛出的异常统一转换为结果集
 *
 * @author 辛凤文
 * @since 1.0
 */
public class ExceptionHelper {

    public static Result<Void> toResult(ServiceException e) {
        if (e.getDesc() == null) {
            return Result.error(e.getError());
        }
        return Result.error(e.getError().getCode(), "[" + e.getError().getCode() + "]-" + e.getDesc());
    }

    public static Result<Void> toResult(BusinessException e) {
        if (e.getDesc() == null) {
            return Result.error(e.getError());
        }
        return Result.error(e.getError().getCode(), "[" + e.getError().getCode() + "]-" + e.getDesc());
    }

    public static Result<Void> toResult() {
        return Result.error(ResultCode.B0001);
    }

}
