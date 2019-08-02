package com.gknoone.cloud.plus.provider.service;

import com.gknoone.cloud.plus.common.core.wrapper.Wrapper;
import com.gknoone.cloud.plus.common.feign.FeignConfiguration;
import com.gknoone.cloud.plus.provider.service.hystrix.HelloFeignApiHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author gknoone
 * @date 2019-07-31 17:58
 */
@FeignClient(value ="provider-hello",configuration = FeignConfiguration.class, fallback = HelloFeignApiHystrix.class)
public interface HelloFeignApi {
    /**
     * sayHello
     * @param somebody sb
     * @return  the wrapper
     */
    @GetMapping(value = "/api/hello/{somebody}")
    Wrapper<String> sayHello(@PathVariable String somebody);
}
