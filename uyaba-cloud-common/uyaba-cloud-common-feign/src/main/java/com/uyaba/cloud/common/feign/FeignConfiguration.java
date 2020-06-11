package com.uyaba.cloud.common.feign;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * feign的Oauth2配置
 *
 * @author uyaba
 * @date 2019-08-01 15:19
 */
@Configuration
public class FeignConfiguration {
    /**
     * 日志级别
     * none：不输出日志
     * basic：只输出请求方法的URL和响应的状态码一级接口的执行时间
     * headers：将basic信息和请求头信息输出
     * full：输出完整的请求信息
     * @return full
     */
    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }
}
