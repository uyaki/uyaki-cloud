package com.gknoone.cloud.plus.provider.apollo;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableApolloConfig
public class ProviderApolloApplication {

    public static void main(String[] args) {
        //指定环境
        System.setProperty("env","DEV");
        SpringApplication.run(ProviderApolloApplication.class, args);
    }

}
