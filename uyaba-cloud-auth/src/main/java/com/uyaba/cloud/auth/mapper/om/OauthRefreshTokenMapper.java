package com.uyaba.cloud.auth.mapper.om;

import com.uyaba.cloud.auth.model.om.OauthRefreshToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OauthRefreshTokenMapper {
    int insert(OauthRefreshToken record);

    int insertSelective(OauthRefreshToken record);
}