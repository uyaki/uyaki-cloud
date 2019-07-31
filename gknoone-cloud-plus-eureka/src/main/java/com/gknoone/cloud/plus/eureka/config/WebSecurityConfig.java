package com.gknoone.cloud.plus.eureka.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Security配置类
 * @author gknoone
 * @date 2019-07-31 09:29
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Spring Cloud 2.0 以上的security默认启用了csrf检验，要在eurekaServer端配置security的csrf检验为false
        http.csrf().disable();
        //注意：为了可以使用 http://${user}:${password}@${host}:${port}/eureka/ 这种方式登录,所以必须是httpBasic
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
    }
}
