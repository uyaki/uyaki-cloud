package com.gknoone.cloud.plus.microservices.service;

import com.gknoone.cloud.plus.microservices.model.test.TestType;

/**
 * @author gknoone
 * @date 2019-09-08 15:03
 */
public interface TestTypeService {
    TestType getTestType(Long id);

    int addTestType(TestType testType);
}
