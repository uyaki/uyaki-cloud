package com.gknoone.cloud.plus.gateway.hystrix;

import com.gknoone.cloud.plus.common.core.wrapper.WrapMapper;
import com.gknoone.cloud.plus.common.core.wrapper.Wrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 统一回退接口
 *
 * @author gknoone
 * @date 2019-08-06 16:26
 */
@RestController
public class FallbackController {
    @GetMapping("/fallback")
    public Wrapper<String> fallback(){
        return WrapMapper.error("熔断回退了");
    }
}
