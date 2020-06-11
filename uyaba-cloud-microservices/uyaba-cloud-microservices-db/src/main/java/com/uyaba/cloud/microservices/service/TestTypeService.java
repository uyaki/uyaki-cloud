package com.uyaba.cloud.microservices.service;

import com.uyaba.cloud.microservices.model.test.TestType;

/**
 * @author uyaba
 * @date 2019-09-08 15:03
 */
public interface TestTypeService {
    TestType getTestType(Long id);

    int addTestType(TestType testType);
}
