package com.uyaki.cloud.microservices.mapper.test;

import com.uyaki.cloud.microservices.model.test.TestType;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestTypeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TestType record);

    int insertSelective(TestType record);

    TestType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TestType record);

    int updateByPrimaryKey(TestType record);
}