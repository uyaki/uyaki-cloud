package com.uyaba.cloud.auth.model.om;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class OauthRefreshToken implements Serializable {
    private String tokenId;

    private byte[] token;

    private byte[] authentication;

    private static final long serialVersionUID = 1L;
}