package com.gknoone.cloud.plus.provider.service.hystrix;

import com.gknoone.cloud.plus.common.core.wrapper.WrapMapper;
import com.gknoone.cloud.plus.common.core.wrapper.Wrapper;
import com.gknoone.cloud.plus.provider.service.HelloFeignApi;
import org.springframework.stereotype.Component;

/**
 * fallback模式可以正常熔断，但是无法抛出异常
 * @author gknoone
 * @date 2019-08-02 08:45
 */
@Component
public class HelloFeignApiHystrix implements HelloFeignApi {
    @Override
    public Wrapper<String> sayHello(String somebody) {
        return WrapMapper.error(somebody+"熔断了");
    }
}
