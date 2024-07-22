package com.shine.share.protocol.web;

import lombok.Data;

/**
 * TODO to describe Result
 *
 * @author 辛凤文
 * @since 1.0
 */
@Data
public class Result<T> {

    private String code;

    private String msg;

    private T data;

    public Result() {
    }

    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    //TODO
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        //result.setCode(HttpStatus.SUCCESS);
        //result.setMsg("success");
        return result;
    }


}
