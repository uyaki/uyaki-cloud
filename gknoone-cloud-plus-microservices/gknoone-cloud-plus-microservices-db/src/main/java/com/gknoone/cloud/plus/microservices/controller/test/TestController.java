package com.gknoone.cloud.plus.microservices.controller.test;

import com.gknoone.cloud.plus.common.core.wrapper.WrapMapper;
import com.gknoone.cloud.plus.common.core.wrapper.Wrapper;
import com.gknoone.cloud.plus.microservices.model.test.TestNormal;
import com.gknoone.cloud.plus.microservices.model.test.TestType;
import com.gknoone.cloud.plus.microservices.service.TestNormalService;
import com.gknoone.cloud.plus.microservices.service.TestTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author gknoone
 * @date 2019-09-08 14:12
 */
@Api(tags = "DB")
@RestController
@RequestMapping("/db")
public class TestController {
    @Autowired
    private TestNormalService testNormalService;

    @Autowired
    private TestTypeService testTypeService;

    @ApiOperation(value = "normal测试",produces = "application/json")
    @GetMapping("/normal/{id}")
    public Wrapper<TestNormal> getTestNormal (@PathVariable Long id){
        return WrapMapper.ok(testNormalService.getTestNormal(id));
    }
    @ApiOperation(value = "type测试",produces = "application/json")
    @GetMapping("/type/{id}")
    public Wrapper<TestType> getTestType (@PathVariable Long id){
        return WrapMapper.ok(testTypeService.getTestType(id));
    }

    @ApiOperation(value = "type添加测试",produces = "application/json")
    @PostMapping("/type/")
    public Wrapper<Integer> addTestType(@RequestBody TestType testType) {
        return WrapMapper.ok(testTypeService.addTestType(testType));
    }

}
