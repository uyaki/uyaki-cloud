package com.uyaba.cloud.gateway;

import com.uyaba.cloud.gateway.resolver.IpKeyResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
    @Bean
    public IpKeyResolver ipKeyResolver(){
        return new IpKeyResolver();
    }
}
