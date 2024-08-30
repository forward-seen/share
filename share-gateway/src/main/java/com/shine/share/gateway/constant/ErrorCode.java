package com.shine.share.gateway.constant;

import com.shine.share.protocol.constant.ErrorDefinition;
import lombok.Getter;

/**
 * 错误码：
 * 1. 五位组成
 * 2. A代表用户端错误
 * 3. B代表当前系统异常
 * 4. C代表第三方服务异常
 * 5. 若无法确定具体错误，选择宏观错误
 * 6. 大的错误类间的步长间距预留100
 *
 * @author 辛凤文
 * @since 1.0
 */
@Getter
public enum ErrorCode implements ErrorDefinition {

    A0240("A0240", "验证码不能为空"),
    A0241("A0241", "验证码已失效"),
    A0242("A0242", "验证码错误"),
    A0243("A0243", "验证码尝试次数超限"),

    B0200("B0200", "网关服务出错"),
    B0204("B0204", "服务未找到"),
    B0205("B0205", "响应状态异常"),

    B0240("B0240", "不支持的验证码类型");

    private final String code;

    private final String desc;

    ErrorCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "[" + this.code + "]-" + this.desc;
    }

}
