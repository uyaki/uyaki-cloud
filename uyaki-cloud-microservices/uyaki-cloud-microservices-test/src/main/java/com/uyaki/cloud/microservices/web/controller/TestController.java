package com.uyaki.cloud.microservices.web.controller;

import com.uyaki.cloud.common.core.wrapper.Wrapper;
import com.uyaki.cloud.microservices.service.HelloFeignApi;
import com.uyaki.cloud.microservices.service.HiFactoryFeignApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author uyaki
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
