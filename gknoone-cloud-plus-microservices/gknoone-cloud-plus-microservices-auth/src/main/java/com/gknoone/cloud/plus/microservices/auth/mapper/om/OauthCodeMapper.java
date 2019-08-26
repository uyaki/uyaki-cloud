package com.gknoone.cloud.plus.microservices.auth.mapper.om;

import com.gknoone.cloud.plus.microservices.auth.model.om.OauthCode;

public interface OauthCodeMapper {
    int insert(OauthCode record);

    int insertSelective(OauthCode record);
}