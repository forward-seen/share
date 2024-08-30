package com.shine.share.protocol.helper;

import com.shine.share.protocol.constant.Error;
import com.shine.share.protocol.web.Result;

/**
 * RPC远程调用结果处理
 *
 * @author 辛凤文
 * @since 1.0
 */
public class RemoteHelper {

    public static <T> boolean success(Result<T> res) {
        if (res == null) {
            return false;
        }
        return Error.SUCCESS.getCode().equals(res.getCode());
    }

    public static <T> boolean fail(Result<T> res) {
        return !success(res);
    }

}
