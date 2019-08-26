package com.gknoone.cloud.plus.microservices.service.impl;

import com.gknoone.cloud.plus.microservices.model.User;
import com.gknoone.cloud.plus.microservices.service.UserService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gknoone
 * @date 2019-08-23 10:47
 */
@Service
@CacheConfig(cacheNames = "User")
public class UserServiceImpl implements UserService {
    @Override
    @Cacheable(key = "#id", unless = "#result==null")
    public User findByIdCache(String id) {
        return null;
    }

    @Override
    @CachePut(key = "#user.id", condition = "#user!=null")
    public User cacheSave(User user) {
        return user;
    }

    @Override
    @CacheEvict(key = "#id")
    public boolean deleteByIdCache(String id) {
        return false;
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean saveBatchCache(List<User> userList) {
        return false;
    }
}
