package com.uyaki.cloud.auth.model.om;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class OauthCode implements Serializable {
    private String code;

    private byte[] authentication;

    private static final long serialVersionUID = 1L;

}