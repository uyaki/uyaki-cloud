package com.uyaba.cloud.microservices.service.impl;

import com.uyaba.cloud.microservices.mapper.test.TestTypeMapper;
import com.uyaba.cloud.microservices.model.test.TestType;
import com.uyaba.cloud.microservices.service.TestTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author uyaba
 * @date 2019-09-08 15:16
 */
@Service
public class TestTypeServiceImpl implements TestTypeService {
    @Autowired
    private TestTypeMapper testTypeMapper;
    @Override
    public TestType getTestType(Long id) {
        return testTypeMapper.selectByPrimaryKey(id);
    }

    @Override
    public int addTestType(TestType testType) {
        return testTypeMapper.insertSelective(testType);
    }
}
