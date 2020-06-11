package com.uyaba.cloud.microservices.service;

import com.uyaba.cloud.microservices.model.test.TestNormal;

/**
 * @author uyaba
 * @date 2019-09-08 11:41
 */
public interface TestNormalService {
    /**
     * 获取简单示例
     * @param id id
     * @return 返回值
     */
    TestNormal getTestNormal(Long id);
}
