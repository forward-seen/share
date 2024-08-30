package com.shine.share.web.controller;


import com.shine.share.protocol.constant.Error;
import com.shine.share.protocol.web.Result;

/**
 * 实现该接口使用success或error方法传递参数封装统一返回结果集
 *
 * @author 辛凤文
 * @since 1.0
 */
public class BaseController {

    public <T> Result<T> error(Error errorCode) {
        return Result.error(errorCode);
    }

    public <T> Result<T> success() {
        return Result.success();
    }

    public <T> Result<T> success(T data) {
        return Result.success(data);
    }

}
