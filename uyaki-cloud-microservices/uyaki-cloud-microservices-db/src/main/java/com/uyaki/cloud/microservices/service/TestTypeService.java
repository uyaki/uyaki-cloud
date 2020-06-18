package com.uyaki.cloud.microservices.service;

import com.uyaki.cloud.microservices.model.test.TestType;

/**
 * @author uyaki
 * @date 2019-09-08 15:03
 */
public interface TestTypeService {
    TestType getTestType(Long id);

    int addTestType(TestType testType);
}
