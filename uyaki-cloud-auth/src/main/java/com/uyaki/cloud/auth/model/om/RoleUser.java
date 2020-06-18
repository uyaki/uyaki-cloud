package com.uyaki.cloud.auth.model.om;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RoleUser implements Serializable {
    private Long id;

    private Long roleid;

    private Long userid;

    private static final long serialVersionUID = 1L;
}