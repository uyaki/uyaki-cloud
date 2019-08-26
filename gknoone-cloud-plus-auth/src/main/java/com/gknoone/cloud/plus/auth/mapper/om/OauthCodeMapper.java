package com.gknoone.cloud.plus.auth.mapper.om;

import com.gknoone.cloud.plus.auth.model.om.OauthCode;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OauthCodeMapper {
    int insert(OauthCode record);

    int insertSelective(OauthCode record);
}