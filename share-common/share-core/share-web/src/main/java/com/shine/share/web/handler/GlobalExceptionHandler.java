package com.shine.share.web.handler;

import com.shine.share.protocol.constant.Error;
import com.shine.share.protocol.exception.BusinessException;
import com.shine.share.protocol.exception.ServiceException;
import com.shine.share.protocol.helper.ExceptionHelper;
import com.shine.share.protocol.web.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常捕获
 *
 * @author 辛凤文
 * @since 1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    public Result<Void> errorResult(ServiceException e) {
        log.error("[{}]-{}, error code : {}", e.getError(), e.getDesc(), e.getError(), e);
        return ExceptionHelper.toResult(e);
    }

    public Result<Void> errorResult(BusinessException e) {
        log.error("[{}]-{}, error code : {}", e.getError(), e.getDesc(), e.getError(), e);
        return ExceptionHelper.toResult(e);
    }

    public Result<Void> errorResult(Exception e) {
        log.error("error code : {}", Error.B0001, e);
        return ExceptionHelper.toResult();
    }

}
