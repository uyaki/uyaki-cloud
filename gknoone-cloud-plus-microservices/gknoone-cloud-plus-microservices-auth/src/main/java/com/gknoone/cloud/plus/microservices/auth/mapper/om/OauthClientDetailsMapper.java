package com.gknoone.cloud.plus.microservices.auth.mapper.om;

import com.gknoone.cloud.plus.microservices.auth.model.om.OauthClientDetails;

public interface OauthClientDetailsMapper {
    int deleteByPrimaryKey(String clientId);

    int insert(OauthClientDetails record);

    int insertSelective(OauthClientDetails record);

    OauthClientDetails selectByPrimaryKey(String clientId);

    int updateByPrimaryKeySelective(OauthClientDetails record);

    int updateByPrimaryKey(OauthClientDetails record);
}