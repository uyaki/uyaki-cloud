package com.gknoone.cloud.plus.microservices.service.impl;

import com.gknoone.cloud.plus.microservices.mapper.test.TestTypeMapper;
import com.gknoone.cloud.plus.microservices.model.test.TestType;
import com.gknoone.cloud.plus.microservices.service.TestTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gknoone
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
