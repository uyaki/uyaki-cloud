package com.gknoone.cloud.plus.provider.service.hystrix;

import com.gknoone.cloud.plus.common.core.wrapper.WrapMapper;
import com.gknoone.cloud.plus.common.core.wrapper.Wrapper;
import com.gknoone.cloud.plus.provider.service.HiFactoryFeignApi;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * fallbackFactory模式可以正常熔断且抛出异常
 *
 * @author gknoone
 * @date 2019-08-02 09:35
 */
@Component
public class HiFactoryFeignApiHystrix implements FallbackFactory<HiFactoryFeignApi> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public HiFactoryFeignApi create(Throwable throwable) {
        logger.error("fallback reason: ",throwable);
        return new HiFactoryFeignApi() {
            @Override
            public Wrapper<String> sayHi(String somebody) {
                return WrapMapper.error("hi"+somebody+"熔断了") ;
            }
        };
    }
}
