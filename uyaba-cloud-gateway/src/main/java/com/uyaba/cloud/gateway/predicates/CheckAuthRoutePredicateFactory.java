package com.uyaba.cloud.gateway.predicates;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.function.Predicate;

/**
 * 检查Auth断言工厂
 *
 * @author uyaba
 * @date 2019-08-05 15:49
 */
@Component
public class CheckAuthRoutePredicateFactory extends AbstractRoutePredicateFactory<CheckAuthRoutePredicateFactory.Config> {
    public CheckAuthRoutePredicateFactory() {
        super(Config.class);
    }

    private final static String AUTH_NAME = "uyaba";


    static class Config{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return serverWebExchange -> {
            System.err.println(String.format("进入了CheckAuthRoutePredicateFactory\t%s", config.getName()));
            return AUTH_NAME.equals(config.getName());
        };
    }
}
