package com.gknoone.cloud.plus.gateway.resolver;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * IP限流
 *
 * @author gknoone
 * @date 2019-08-06 11:10
 */
public class IpKeyResolver implements KeyResolver {
    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return Mono.just(getIpAddr(exchange.getRequest()));
    }

    /**
     * 获取真实IP
     * @param request 请求
     * @return ip
     */
    private static String getIpAddr(ServerHttpRequest request){
        HttpHeaders httpHeaders  = request.getHeaders();
        List<String> ips = httpHeaders.get("X-Forwarded-For");
        String ip = "127.0.0.1";
        if(ips!= null && ips.size()>0){
            ip = ips.get(0);
        }
        return ip;
    }
}
