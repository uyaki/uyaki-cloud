package com.uyaba.cloud.auth.mapper.om;


import com.uyaba.cloud.auth.model.om.RoleUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RoleUser record);

    int insertSelective(RoleUser record);

    RoleUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RoleUser record);

    int updateByPrimaryKey(RoleUser record);
}