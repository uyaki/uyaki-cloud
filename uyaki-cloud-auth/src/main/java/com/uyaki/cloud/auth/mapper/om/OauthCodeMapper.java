package com.uyaki.cloud.auth.mapper.om;

import com.uyaki.cloud.auth.model.om.OauthCode;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OauthCodeMapper {
    int insert(OauthCode record);

    int insertSelective(OauthCode record);
}