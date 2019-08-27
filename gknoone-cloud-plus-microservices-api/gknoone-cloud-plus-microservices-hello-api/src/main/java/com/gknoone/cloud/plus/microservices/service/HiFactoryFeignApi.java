package com.gknoone.cloud.plus.microservices.service;

import com.gknoone.cloud.plus.common.core.wrapper.Wrapper;
import com.gknoone.cloud.plus.common.feign.FeignConfiguration;
import com.gknoone.cloud.plus.microservices.service.hystrix.HiFactoryFeignApiHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 演示fallback工厂Api
 *
 * @author gknoone
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
    Wrapper<String> sayHi(@PathVariable String somebody);
}
