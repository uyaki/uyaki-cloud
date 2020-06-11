package com.uyaba.cloud.microservices.auth.mapper.om;

import com.uyaba.cloud.microservices.auth.model.om.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 根据用户名获取用户
     * @param name 用户名
     * @return 用户
     */
    User getUserByName(@Param("name") String name);
}