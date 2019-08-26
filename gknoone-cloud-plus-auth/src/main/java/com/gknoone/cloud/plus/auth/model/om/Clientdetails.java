package com.gknoone.cloud.plus.auth.model.om;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Clientdetails implements Serializable {
    private String appid;

    private String resourceids;

    private String appsecret;

    private String scope;

    private String granttypes;

    private String redirecturl;

    private String authorities;

    private Long accessTokenValidity;

    private Long refreshTokenValidity;

    private String additionalinformation;

    private String autoapprovescopes;

    private static final long serialVersionUID = 1L;
}