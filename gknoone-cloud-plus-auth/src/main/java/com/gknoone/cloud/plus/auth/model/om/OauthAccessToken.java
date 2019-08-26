package com.gknoone.cloud.plus.auth.model.om;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class OauthAccessToken implements Serializable {
    private String authenticationId;

    private String tokenId;

    private String userName;

    private String clientId;

    private String refreshToken;

    private byte[] token;

    private byte[] authentication;

    private static final long serialVersionUID = 1L;

}