package com.gknoone.cloud.plus.microservices.service.impl;

import com.gknoone.cloud.plus.microservices.mapper.test.TestNormalMapper;
import com.gknoone.cloud.plus.microservices.model.test.TestNormal;
import com.gknoone.cloud.plus.microservices.service.TestNormalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gknoone
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
