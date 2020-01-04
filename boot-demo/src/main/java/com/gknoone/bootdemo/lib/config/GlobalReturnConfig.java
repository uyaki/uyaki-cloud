package com.benyuan.bootdemo.lib.config;

import com.benyuan.bootdemo.lib.util.ResponseDTO;
import com.benyuan.bootdemo.lib.util.ResponseDTOUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @Description 全局返回值统一封装
 *  */
@Configuration
public class GlobalReturnConfig {
    @RestControllerAdvice(basePackages = "com.benyuan")
    static class ResultResponseAdvice implements ResponseBodyAdvice<Object> {
        @Override
        public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
            return true;
        }

        @Override
        public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
            if (body instanceof ResponseDTO) {
                return body;
            }
            return ResponseDTOUtil.success(body);
        }
    }

}
