package com.uyaba.cloud.microservices.web.controller;

import com.uyaba.cloud.common.core.wrapper.WrapMapper;
import com.uyaba.cloud.common.core.wrapper.Wrapper;
import com.uyaba.cloud.microservices.model.User;
import com.uyaba.cloud.microservices.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author uyaba
 * @date 2019-08-23 10:41
 */
@Api(tags = "Redis 测试API")
@RestController
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取用户", produces = "application/json")
    @GetMapping("/user/{id}")
    public Wrapper<User> getUser(@PathVariable String id) {
        return WrapMapper.ok(userService.findByIdCache(id));
    }

    @ApiOperation(value = "添加用户", produces = "application/json")
    @PostMapping("/user")
    public Wrapper<User> addUser(@RequestBody User user) {
        return WrapMapper.ok(userService.cacheSave(user));
    }

    @ApiOperation(value = "删除用户", produces = "application/json")
    @DeleteMapping("/user/{id}")
    public Wrapper<Boolean> deleteUser(@PathVariable String id) {
        return WrapMapper.ok(userService.deleteByIdCache(id));
    }

    @ApiOperation(value = "批量添加用户", produces = "application/json")
    @PostMapping("/users")
    public Wrapper<Boolean> deleteUser(@RequestBody List<User> userList) {
        return WrapMapper.ok(userService.saveBatchCache(userList));
    }

}
