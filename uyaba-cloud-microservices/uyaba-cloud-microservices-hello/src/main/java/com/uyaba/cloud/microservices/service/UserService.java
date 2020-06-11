package com.uyaba.cloud.microservices.service;

import com.uyaba.cloud.microservices.model.User;

import java.util.List;

/**
 * 测试Redis用
 *
 * @author uyaba
 * @date 2019-08-23 10:47
 */
public interface UserService {

    User findByIdCache(String Id);

    User cacheSave(User user);

    boolean deleteByIdCache(String Id);
    boolean saveBatchCache(List<User> userList);

}
