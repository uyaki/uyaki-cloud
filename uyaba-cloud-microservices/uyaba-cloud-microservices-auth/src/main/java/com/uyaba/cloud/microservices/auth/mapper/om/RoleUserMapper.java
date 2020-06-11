package com.uyaba.cloud.microservices.auth.mapper.om;

import com.uyaba.cloud.microservices.auth.model.om.RoleUser;

public interface RoleUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RoleUser record);

    int insertSelective(RoleUser record);

    RoleUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RoleUser record);

    int updateByPrimaryKey(RoleUser record);
}