package com.gknoone.cloud.plus.auth.mapper.om;

import com.gknoone.cloud.plus.auth.model.om.OauthApprovals;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OauthApprovalsMapper {
    int insert(OauthApprovals record);

    int insertSelective(OauthApprovals record);
}