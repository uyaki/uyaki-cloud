package com.gknoone.cloud.plus.auth.mapper.om;


import com.gknoone.cloud.plus.auth.model.om.OauthAccessToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OauthAccessTokenMapper {
    int deleteByPrimaryKey(String authenticationId);

    int insert(OauthAccessToken record);

    int insertSelective(OauthAccessToken record);

    OauthAccessToken selectByPrimaryKey(String authenticationId);

    int updateByPrimaryKeySelective(OauthAccessToken record);

    int updateByPrimaryKeyWithBLOBs(OauthAccessToken record);

    int updateByPrimaryKey(OauthAccessToken record);
}