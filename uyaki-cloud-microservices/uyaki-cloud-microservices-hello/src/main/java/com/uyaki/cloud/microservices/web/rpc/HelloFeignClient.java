package com.uyaki.cloud.microservices.web.rpc;

import com.uyaki.cloud.common.core.support.BaseController;
import com.uyaki.cloud.common.core.wrapper.WrapMapper;
import com.uyaki.cloud.common.core.wrapper.Wrapper;
import com.uyaki.cloud.microservices.service.HelloFeignApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author uyaki
 * @date 2019-07-31 18:52
 */
@Api(tags = "Hello Feign Client")
@RestController
public class HelloFeignClient extends BaseController implements HelloFeignApi {


    @ApiOperation(value = "say hello to sb")
    @Override
    public Wrapper<String> sayHello(@PathVariable String somebody) {
        return WrapMapper.ok("hello"+somebody);
    }
}
