package com.uyaki.cloud.microservices.auth.mapper.om;

import com.uyaki.cloud.microservices.auth.model.om.OauthCode;

public interface OauthCodeMapper {
    int insert(OauthCode record);

    int insertSelective(OauthCode record);
}