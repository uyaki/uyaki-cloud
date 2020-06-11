package com.uyaba.cloud.microservices.service.impl;

import com.uyaba.cloud.microservices.mapper.test.TestNormalMapper;
import com.uyaba.cloud.microservices.model.test.TestNormal;
import com.uyaba.cloud.microservices.service.TestNormalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author uyaba
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
