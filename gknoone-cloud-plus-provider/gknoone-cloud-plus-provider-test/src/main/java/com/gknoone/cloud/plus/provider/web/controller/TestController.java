package com.gknoone.cloud.plus.provider.web.controller;

import com.gknoone.cloud.plus.common.core.wrapper.Wrapper;
import com.gknoone.cloud.plus.provider.service.HelloFeignApi;
import com.gknoone.cloud.plus.provider.service.HiFactoryFeignApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author gknoone
 * @date 2019-07-31 18:06
 */
@Api(tags = "Test Controller")
@RestController
@RequestMapping(value = "/test",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TestController {

    @Resource
    private HelloFeignApi helloFeignApi;
    @Resource
    private HiFactoryFeignApi hiFactoryFeignApi;

    @ApiOperation(value = "say hello")
    @GetMapping(value = "/hello")
    public Wrapper<String> sayHello(){
        return helloFeignApi.sayHello("test");
    }
    @ApiOperation(value = "say hi")
    @GetMapping(value = "/hi")
    public Wrapper<String> sayHi(){
        return hiFactoryFeignApi.sayHi("test");
    }
}
