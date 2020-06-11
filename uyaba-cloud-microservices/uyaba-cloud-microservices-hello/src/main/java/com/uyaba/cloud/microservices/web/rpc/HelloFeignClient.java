package com.uyaba.cloud.microservices.web.rpc;

import com.uyaba.cloud.common.core.support.BaseController;
import com.uyaba.cloud.common.core.wrapper.WrapMapper;
import com.uyaba.cloud.common.core.wrapper.Wrapper;
import com.uyaba.cloud.microservices.service.HelloFeignApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author uyaba
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
