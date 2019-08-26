package com.gknoone.cloud.plus.microservices.auth.mapper.om;

import com.gknoone.cloud.plus.microservices.auth.model.om.OauthRefreshToken;

public interface OauthRefreshTokenMapper {
    int insert(OauthRefreshToken record);

    int insertSelective(OauthRefreshToken record);
}