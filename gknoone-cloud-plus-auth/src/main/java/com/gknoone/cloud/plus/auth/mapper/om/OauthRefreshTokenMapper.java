package com.gknoone.cloud.plus.auth.mapper.om;

import com.gknoone.cloud.plus.auth.model.om.OauthRefreshToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OauthRefreshTokenMapper {
    int insert(OauthRefreshToken record);

    int insertSelective(OauthRefreshToken record);
}