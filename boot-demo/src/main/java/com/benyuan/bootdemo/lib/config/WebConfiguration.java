package com.benyuan.bootdemo.lib.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 使用RestControllerAdvice的beforeBodyWrite方法时，在处理String时，实际处理的HttpMessageConverter，应该是MappingJackson2HttpMessageConverter
 * @Description
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 0-1可配，2-8是默认加载的
        converters.add(0, new MappingJackson2HttpMessageConverter());
    }
}
