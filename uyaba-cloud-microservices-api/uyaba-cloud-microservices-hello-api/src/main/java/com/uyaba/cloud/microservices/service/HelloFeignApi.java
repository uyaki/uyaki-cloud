package com.uyaba.cloud.microservices.service;

import com.uyaba.cloud.common.core.wrapper.Wrapper;
import com.uyaba.cloud.common.feign.FeignConfiguration;
import com.uyaba.cloud.microservices.service.hystrix.HelloFeignApiHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author uyaba
 * @date 2019-07-31 17:58
 */
@FeignClient(value ="microservices-hello",configuration = FeignConfiguration.class, fallback = HelloFeignApiHystrix.class)
public interface HelloFeignApi {
    /**
     * sayHello
     * @param somebody sb
     * @return  the wrapper
     */
    @GetMapping(value = "/api/hello/{somebody}")
    Wrapper<String> sayHello(@PathVariable("somebody") String somebody);
}
