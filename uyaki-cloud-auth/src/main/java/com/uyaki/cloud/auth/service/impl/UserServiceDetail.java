package com.uyaki.cloud.auth.service.impl;

import com.uyaki.cloud.auth.mapper.om.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author uyaki
 * @date 2019-08-20 09:36
 */
public class UserServiceDetail implements UserDetailsService {
    @Autowired
    UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userMapper.getUserByName(s);
    }
}
