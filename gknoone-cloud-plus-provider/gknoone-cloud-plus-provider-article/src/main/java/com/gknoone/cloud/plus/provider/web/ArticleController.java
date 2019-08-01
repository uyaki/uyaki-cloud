package com.gknoone.cloud.plus.provider.web;

import com.gknoone.cloud.plus.common.core.wrapper.Wrapper;
import com.gknoone.cloud.plus.provider.service.UserFeignApi;
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
@RequestMapping(value = "/article",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ArticleController {

    @Resource
    private UserFeignApi userFeignApi;

    @GetMapping(value = "/hello")
    public Wrapper<String> sayHello(){
        return userFeignApi.sayHello("article");
    }
}
