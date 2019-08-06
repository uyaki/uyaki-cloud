package com.gknoone.cloud.plus.gateway.filter;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * IP全局过滤器
 *
 * @author gknoone
 * @date 2019-08-06 09:09
 */
@Component
public class IpGlobalFilter implements GlobalFilter, Ordered {
    private static final String LOCAL_IP = "127.0.0.1";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders httpHeaders = exchange.getRequest().getHeaders();
        if (!LOCAL_IP.equals(getIp(httpHeaders))) {
            ServerHttpResponse response = exchange.getResponse();
            ResponseDate responseDate = new ResponseDate();
            responseDate.setCode(401);
            responseDate.setMessage("非法请求");
            byte[] data = JSON.toJSONBytes(responseDate);
            DataBuffer dataBuffer = response.bufferFactory().wrap(data);
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
            return response.writeWith(Mono.just(dataBuffer));
        }
        return chain.filter(exchange);
    }

    private String getIp(HttpHeaders httpHeaders) {
        return "127.0.0.1";
    }

    @Data
    private class ResponseDate {
        int code;
        String message;
    }

    /**
     * 数字越小，优先级越高
     *
     * @return order
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
