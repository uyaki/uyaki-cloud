package com.gknoone.cloud.plus.provider.web;

import com.gknoone.cloud.plus.common.core.wrapper.Wrapper;
import com.gknoone.cloud.plus.provider.service.HelloFeignApi;
import com.gknoone.cloud.plus.provider.service.HiFactoryFeignApi;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author gknoone
 * @date 2019-07-31 18:06
 */
@RestController
@RequestMapping(value = "/test",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TestController {

    @Resource
    private HelloFeignApi helloFeignApi;
    @Resource
    private HiFactoryFeignApi hiFactoryFeignApi;

    @GetMapping(value = "/hello")
    public Wrapper<String> sayHello(){
        return helloFeignApi.sayHello("test");
    }
    @GetMapping(value = "/hi")
    public Wrapper<String> sayHi(){
        return hiFactoryFeignApi.sayHi("test");
    }
}
