package com.uyaba.cloud.auth.model.om;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class OauthApprovals implements Serializable {
    private String userid;

    private String clientid;

    private String scope;

    private String status;

    private Date expiresat;

    private Date lastmodifiedat;

    private static final long serialVersionUID = 1L;

}