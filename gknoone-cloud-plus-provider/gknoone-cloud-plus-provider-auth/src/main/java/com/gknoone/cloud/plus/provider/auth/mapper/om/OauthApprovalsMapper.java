package com.gknoone.cloud.plus.provider.auth.mapper.om;

import com.gknoone.cloud.plus.provider.auth.model.om.OauthApprovals;

public interface OauthApprovalsMapper {
    int insert(OauthApprovals record);

    int insertSelective(OauthApprovals record);
}