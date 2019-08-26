package com.gknoone.cloud.plus.microservices.nacos.config.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gknoone
 * @date 2019-08-14 14:52
 */
@RestController
@RefreshScope
public class NacosConfigController {
    @Value("${user.name:kk}")
    private String name;
    @Value("${user.age:11}")
    private Integer age;

    @GetMapping("/user")
    public String getUserInfo(){
        return String.format("%s is %s years old this year.", name,age);
    }
}
