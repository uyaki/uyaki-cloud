package com.gknoone.cloud.plus.provider.web;

import com.gknoone.cloud.plus.common.core.wrapper.WrapMapper;
import com.gknoone.cloud.plus.common.core.wrapper.Wrapper;
import com.gknoone.cloud.plus.provider.service.HiFactoryFeignApi;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hi
 *
 * @author gknoone
 * @date 2019-08-02 09:48
 */
@RestController
public class HiFeignClient implements HiFactoryFeignApi {

    @Override
    public Wrapper<String> sayHi(String somebody) {
        return WrapMapper.ok("hi"+somebody);
    }
}
