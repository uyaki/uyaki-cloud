package com.gknoone.cloud.plus.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProviderHelloApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderHelloApplication.class, args);
    }

}
