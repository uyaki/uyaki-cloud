package com.gknoone.cloud.plus.zuul.controller;

import com.gknoone.cloud.plus.common.core.wrapper.WrapMapper;
import com.gknoone.cloud.plus.common.core.wrapper.Wrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gknoone
 * @date 2019-08-21 14:22
 */
@RestController
public class LocalController {
    @GetMapping("/local/{somebody}")
    public Wrapper<String> getLocal(@PathVariable String somebody){
        return WrapMapper.ok("local:"+somebody);
    }
}
