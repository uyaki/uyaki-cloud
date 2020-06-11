package com.uyaba.cloud.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

/**
 * 全局过滤器配置类
 *
 * @author uyaba
 * @date 2019-08-06 09:00
 */
@Configuration
public class ExampleConfiguration {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Bean
    @Order(-1)
    public GlobalFilter a(){
        return (exchange, chain) -> {
            logger.info("first pre filter");
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                logger.info("third post filter");
            }));
        };
    }
    @Bean
    @Order(0)
    public GlobalFilter b(){
        return (exchange, chain) -> {
            logger.info("second pre filter");
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                logger.info("second post filter");
            }));
        };
    }
    @Bean
    @Order(1)
    public GlobalFilter c(){
        return (exchange, chain) -> {
            logger.info("third pre filter");
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                logger.info("first post filter");
            }));
        };
    }
}
