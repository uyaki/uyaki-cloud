package com.gknoone.cloud.plus.microservices.auth.mapper.om;

import com.gknoone.cloud.plus.microservices.auth.model.om.OauthApprovals;

public interface OauthApprovalsMapper {
    int insert(OauthApprovals record);

    int insertSelective(OauthApprovals record);
}