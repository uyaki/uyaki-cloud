package com.gknoone.cloud.plus.provider.auth.mapper.om;

import com.gknoone.cloud.plus.provider.auth.model.om.OauthCode;

public interface OauthCodeMapper {
    int insert(OauthCode record);

    int insertSelective(OauthCode record);
}