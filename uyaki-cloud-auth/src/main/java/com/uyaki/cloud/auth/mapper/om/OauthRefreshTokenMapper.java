package com.uyaki.cloud.auth.mapper.om;

import com.uyaki.cloud.auth.model.om.OauthRefreshToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OauthRefreshTokenMapper {
    int insert(OauthRefreshToken record);

    int insertSelective(OauthRefreshToken record);
}