package com.uyaki.cloud.zuul.config;

import com.uyaki.cloud.zuul.filter.IpFilter;
import com.uyaki.cloud.zuul.filter.ResquestMsgFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 过滤器配置
 *
 * @author uyaki
 * @date 2019-08-21 16:32
 */
@Configuration
public class FilterConfig {
    @Bean
    public IpFilter ipFilter(){
        return new IpFilter();
    }
    @Bean
    public ResquestMsgFilter requestMsgFilter(){
        return new ResquestMsgFilter();
    }
}
