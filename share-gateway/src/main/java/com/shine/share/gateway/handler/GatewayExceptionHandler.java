package com.shine.share.gateway.handler;

import com.shine.share.gateway.constant.ErrorCode;
import com.shine.share.gateway.util.WebServerUtils;
import com.shine.share.protocol.constant.ErrorDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关统一异常处理
 *
 * @author 辛凤文
 * @since 1.0
 */
@Slf4j
@Order(-1)
@Configuration
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();

        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }

        ErrorDefinition error;

        if (ex instanceof NotFoundException) {
            error = ErrorCode.B0204;
        } else if (ex instanceof ResponseStatusException responseStatusException) {
            error = ErrorCode.B0205;
        } else {
            error = ErrorCode.B0200;
        }

        log.error("[网关异常处理]请求路径:{},异常信息:{}", exchange.getRequest().getPath(), ex.getMessage());
        return WebServerUtils.webFluxResponseWriter(response, error);
    }
}