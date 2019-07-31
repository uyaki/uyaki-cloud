package com.gknoone.cloud.plus.provider.user.controller;

import com.gknoone.cloud.plus.common.wrapper.WrapMapper;
import com.gknoone.cloud.plus.common.wrapper.Wrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gknoone
 * @date 2019-07-31 15:13
 */
@RestController
public class UserController {
    @GetMapping(value = "/hello/{somebody}",produces = "application/json")
    public Wrapper<String> sayHello(@PathVariable String somebody){
        return WrapMapper.ok(somebody);
    }
}
