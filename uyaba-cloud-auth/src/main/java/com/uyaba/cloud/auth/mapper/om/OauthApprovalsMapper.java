package com.uyaba.cloud.auth.mapper.om;

import com.uyaba.cloud.auth.model.om.OauthApprovals;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OauthApprovalsMapper {
    int insert(OauthApprovals record);

    int insertSelective(OauthApprovals record);
}