package com.gknoone.cloud.plus.provider.auth.mapper.om;

import com.gknoone.cloud.plus.provider.auth.model.om.Role;

public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}