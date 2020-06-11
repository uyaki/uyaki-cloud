package com.uyaba.cloud.microservices.web.rpc;

import com.uyaba.cloud.common.core.wrapper.WrapMapper;
import com.uyaba.cloud.common.core.wrapper.Wrapper;
import com.uyaba.cloud.microservices.service.HiFactoryFeignApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hi
 *
 * @author uyaba
 * @date 2019-08-02 09:48
 */

@Api(tags = "hi Feign Client")
@RestController
public class HiFeignClient implements HiFactoryFeignApi {

    @ApiOperation(value = "say hi to sb")
    @Override
    public Wrapper<String> sayHi(String somebody) {
        return WrapMapper.ok("hi"+somebody);
    }
}
