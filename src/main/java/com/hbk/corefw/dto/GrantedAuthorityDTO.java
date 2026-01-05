package com.hbk.corefw.dto;

import org.springframework.security.core.GrantedAuthority;

public class GrantedAuthorityDTO implements GrantedAuthority {

    private String authority;

    public GrantedAuthorityDTO() {

    }

    public GrantedAuthorityDTO(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
