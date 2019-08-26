package com.gknoone.cloud.plus.auth.model.om;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class OauthClientDetails implements Serializable {
    private String clientId;

    private String resourceIds;

    private String clientSecret;

    private String scope;

    private String authorizedGrantTypes;

    private String webServerRedirectUri;

    private String authorities;

    private Long accessTokenValidity;

    private Long refreshTokenValidity;

    private String additionalInformation;

    private String autoapprove;

    private static final long serialVersionUID = 1L;

}