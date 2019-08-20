package com.gknoone.cloud.plus.provider.apollo.controller;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.gknoone.cloud.plus.provider.apollo.config.UserConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gknoone
 * @date 2019-08-13 14:23
 */
@RestController
public class ApolloController {
    @Autowired
    private UserConfig userConfig;

    @ApolloConfig
    private Config config;
    @GetMapping("/config/username")
    public String getUserName(){
        return userConfig.getUsername()+"--"+config.getProperty("username","bc");
    }
    @GetMapping("/config/sb")
    public String getSB(){
        return "sb";
    }
}
