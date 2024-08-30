package com.shine.share.protocol.web;

import com.shine.share.protocol.constant.Error;
import com.shine.share.protocol.constant.ErrorDefinition;
import lombok.Data;

/**
 * API接口统一返回格式
 *
 * @author 辛凤文
 * @since 1.0
 */
@Data
public class Result<T> implements java.io.Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 1L;

    private String code;

    private String msg;

    private T data;

    public Result() {
    }

    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(T data) {
        this.code = Error.SUCCESS.getCode();
        this.msg = Error.SUCCESS.getDesc();
        this.data = data;
    }

    public Result(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> success() {
        return new Result<>(Error.SUCCESS.getCode(), Error.SUCCESS.getDesc());
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }

    public static <T> Result<T> error(ErrorDefinition error) {
        return new Result<>(error.getCode(), error.getDesc());
    }

    public static <T> Result<T> error(String code, String msg) {
        return new Result<>(code, msg);
    }

}
