package com.uyaki.cloud.microservices.service;

import com.uyaki.cloud.microservices.model.User;

import java.util.List;

/**
 * 测试Redis用
 *
 * @author uyaki
 * @date 2019-08-23 10:47
 */
public interface UserService {

    User findByIdCache(String Id);

    User cacheSave(User user);

    boolean deleteByIdCache(String Id);
    boolean saveBatchCache(List<User> userList);

}
