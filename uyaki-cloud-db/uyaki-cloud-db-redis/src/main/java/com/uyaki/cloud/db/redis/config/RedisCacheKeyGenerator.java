package com.uyaki.cloud.db.redis.config;

import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;


/**
 * Redis缓存key自动生成器
 *
 * @author uyaki
 * @date 2019-08-23 11:37
 */
public class RedisCacheKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object o, Method method, Object... objects) {
            StringBuilder sb = new StringBuilder();
            sb.append(o.getClass().getName());
            sb.append(method.getName());
            for (Object obj : objects) {
                sb.append(obj.toString());
            }
            return sb.toString();
    }
}
