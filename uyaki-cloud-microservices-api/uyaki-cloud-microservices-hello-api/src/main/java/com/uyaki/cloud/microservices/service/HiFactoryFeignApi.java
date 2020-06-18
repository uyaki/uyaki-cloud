package com.uyaki.cloud.microservices.service;

import com.uyaki.cloud.common.core.wrapper.Wrapper;
import com.uyaki.cloud.common.feign.FeignConfiguration;
import com.uyaki.cloud.microservices.service.hystrix.HiFactoryFeignApiHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 演示fallback工厂Api
 *
 * @author uyaki
 * @date 2019-08-02 08:54
 */
@FeignClient(value = "microservices-hello",configuration = FeignConfiguration.class, fallbackFactory = HiFactoryFeignApiHystrix.class)
public interface HiFactoryFeignApi {
    /**
     * say hi
     * @param somebody sb
     * @return wrapper
     */
    @GetMapping("/api/hi/{somebody}")
    Wrapper<String> sayHi(@PathVariable("somebody") String somebody);
}
