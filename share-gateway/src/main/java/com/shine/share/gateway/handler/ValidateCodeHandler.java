package com.shine.share.gateway.handler;

import com.shine.share.gateway.service.ValidateCodeService;
import com.shine.share.protocol.web.Result;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * 验证码获取
 *
 * @author 辛凤文
 * @since 1.0
 */
@Component
public class ValidateCodeHandler implements HandlerFunction<ServerResponse> {

    @Resource
    private ValidateCodeService validateCodeService;

    @Override
    public Mono<ServerResponse> handle(ServerRequest serverRequest) {
        Result<?> res = validateCodeService.createCaptcha();
        return ServerResponse.status(HttpStatus.OK).body(BodyInserters.fromValue(res));
    }
}
