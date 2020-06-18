package com.uyaki.cloud.zuul.controller;

import com.uyaki.cloud.common.core.wrapper.WrapMapper;
import com.uyaki.cloud.common.core.wrapper.Wrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author uyaki
 * @date 2019-08-21 14:22
 */
@RestController
public class LocalController {
    @GetMapping("/local/{somebody}")
    public Wrapper<String> getLocal(@PathVariable String somebody){
        return WrapMapper.ok("local:"+somebody);
    }
}
