package com.uyaba.cloud.zuul.controller;

import com.uyaba.cloud.common.core.wrapper.WrapMapper;
import com.uyaba.cloud.common.core.wrapper.Wrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author uyaba
 * @date 2019-08-21 14:22
 */
@RestController
public class LocalController {
    @GetMapping("/local/{somebody}")
    public Wrapper<String> getLocal(@PathVariable String somebody){
        return WrapMapper.ok("local:"+somebody);
    }
}
