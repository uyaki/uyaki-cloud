package com.gknoone.cloud.plus.auth.controller;

import com.gknoone.cloud.plus.auth.service.AuthService;
import com.gknoone.cloud.plus.common.core.wrapper.WrapMapper;
import com.gknoone.cloud.plus.common.core.wrapper.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gknoone
 * @date 2019-08-09 10:42
 */
@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private AuthService authService;
    @GetMapping("/user/{id}")
    private Wrapper<String> getUser(@PathVariable Long id){
        String user = authService.getUser(id);
        return WrapMapper.ok(user);
    }
}
