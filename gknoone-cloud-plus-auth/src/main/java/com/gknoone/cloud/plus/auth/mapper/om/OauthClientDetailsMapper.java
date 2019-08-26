package com.gknoone.cloud.plus.auth.mapper.om;


import com.gknoone.cloud.plus.auth.model.om.OauthClientDetails;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OauthClientDetailsMapper {
    int deleteByPrimaryKey(String clientId);

    int insert(OauthClientDetails record);

    int insertSelective(OauthClientDetails record);

    OauthClientDetails selectByPrimaryKey(String clientId);

    int updateByPrimaryKeySelective(OauthClientDetails record);

    int updateByPrimaryKey(OauthClientDetails record);
}