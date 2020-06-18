package com.uyaki.cloud.microservices.service.impl;

import com.uyaki.cloud.microservices.mapper.test.TestNormalMapper;
import com.uyaki.cloud.microservices.model.test.TestNormal;
import com.uyaki.cloud.microservices.service.TestNormalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author uyaki
 * @date 2019-09-08 11:41
 */
@Service
public class TestNormalServiceImpl implements TestNormalService {

    @Autowired
    private TestNormalMapper testNormalMapper;

    @Override
    public TestNormal getTestNormal(Long id) {
        return testNormalMapper.selectByPrimaryKey(id);
    }


}
