package com.gknoone.cloud.plus.provider.auth.model.om;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;



public class Role implements GrantedAuthority {
    @Getter
    @Setter
    private Long id;
    @Setter
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}