package com.gknoone.cloud.plus.microservices.apollo;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableApolloConfig
public class ApolloApplication {

    public static void main(String[] args) {
        //指定环境
        System.setProperty("env","DEV");
        SpringApplication.run(ApolloApplication.class, args);
    }

}
