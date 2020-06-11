package com.uyaba.cloud.microservices.auth.mapper.om;

import com.uyaba.cloud.microservices.auth.model.om.OauthApprovals;

public interface OauthApprovalsMapper {
    int insert(OauthApprovals record);

    int insertSelective(OauthApprovals record);
}