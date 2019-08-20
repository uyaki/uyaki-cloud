package com.gknoone.cloud.plus.provider.web.rpc;

import com.gknoone.cloud.plus.common.core.support.BaseController;
import com.gknoone.cloud.plus.common.core.wrapper.WrapMapper;
import com.gknoone.cloud.plus.common.core.wrapper.Wrapper;
import com.gknoone.cloud.plus.provider.service.HelloFeignApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gknoone
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
