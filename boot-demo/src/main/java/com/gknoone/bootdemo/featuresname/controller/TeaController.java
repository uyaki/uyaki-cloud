package com.benyuan.bootdemo.featuresname.controller;

import com.benyuan.bootdemo.featuresname.entity.Tea;
import com.benyuan.bootdemo.featuresname.service.TeaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description TestController
 */
@RestController
@RequestMapping("/tea")
@Api(tags = "喝茶Controller")
public class TeaController {

    private final static Logger logger = LoggerFactory.getLogger(TeaController.class);

    @Resource
    private TeaService teaService;

    /**
     * 测试用接口
     * @return tea
     * @throws JsonProcessingException
     */
    @ApiOperation(value = "接口测试", notes = "接口描述测试",produces = "application/json")
    @GetMapping("/hi")
    public Tea getTea()  {
        return teaService.getTea();
    }

    /**
     * logback 测试
     * @return Str
     */
    @GetMapping("/log")
    public String logback(){
        logger.debug("记录debug日志");
        logger.info("访问了index方法");
        logger.error("记录了error错误日志");
        return "index";
    }
}
