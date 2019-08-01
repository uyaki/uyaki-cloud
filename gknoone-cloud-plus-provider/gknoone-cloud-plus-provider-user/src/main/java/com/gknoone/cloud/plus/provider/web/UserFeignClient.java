package com.gknoone.cloud.plus.provider.web;

import com.gknoone.cloud.plus.common.core.support.BaseController;
import com.gknoone.cloud.plus.common.core.wrapper.WrapMapper;
import com.gknoone.cloud.plus.common.core.wrapper.Wrapper;
import com.gknoone.cloud.plus.provider.service.UserFeignApi;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gknoone
 * @date 2019-07-31 18:52
 */
@RestController
public class UserFeignClient extends BaseController implements UserFeignApi {
    @Override
    public Wrapper<String> sayHello(@PathVariable String somebody) {
        return WrapMapper.ok("hello"+somebody);
    }
}
