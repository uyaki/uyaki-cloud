package com.gknoone.cloud.plus.provider.auth.mapper.om;

import com.gknoone.cloud.plus.provider.auth.model.om.OauthRefreshToken;

public interface OauthRefreshTokenMapper {
    int insert(OauthRefreshToken record);

    int insertSelective(OauthRefreshToken record);
}