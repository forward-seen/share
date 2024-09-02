package com.shine.share.gateway.util;

import com.alibaba.fastjson2.JSON;
import com.shine.share.protocol.constant.ErrorDefinition;
import com.shine.share.protocol.web.Result;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

/**
 * web服务器工具类
 *
 * @author 辛凤文
 * @since 1.0
 */
public class WebServerUtils {

    /**
     * 设置webflux模型响应
     *
     * @param response ServerHttpResponse
     * @param error    响应内容
     * @return Mono<Void>
     */
    public static Mono<Void> webFluxResponseWriter(ServerHttpResponse response, ErrorDefinition error) {
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        Result<?> result = Result.error(error);
        DataBuffer dataBuffer = response.bufferFactory().wrap(JSON.toJSONString(result).getBytes());
        return response.writeWith(Mono.just(dataBuffer));
    }
}
