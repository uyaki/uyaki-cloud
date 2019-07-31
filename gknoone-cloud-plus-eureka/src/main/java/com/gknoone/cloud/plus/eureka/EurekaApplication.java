package com.gknoone.cloud.plus.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 启动类
 * 使用 @EnableEurekaServer 开启EurekaServer
 * @author gknoone
 * @date 2019-07-31 09:01:32
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class, args);
    }

}
