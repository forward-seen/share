package com.shine.share.protocol.helper;

import com.shine.share.protocol.constant.ResultCode;
import com.shine.share.protocol.domain.Result;

/**
 * RPC远程调用结果处理
 * 适用于遵循该约定的服务接口的返回结果状态判断和处理
 *
 * @author 辛凤文
 * @since 1.0
 */
public class RemoteHelper {

    public static <T> boolean success(Result<T> res) {
        if (res == null) {
            return false;
        }
        return ResultCode.SUCCESS.getCode().equals(res.getCode());
    }

    public static <T> boolean fail(Result<T> res) {
        return !success(res);
    }

}
