package com.github.ser.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    SYSTEM_ADMIN(0),
    ORGANIZER_EDITOR(1),
    ORGANIZER_VIEWER(2),
    COMPANY_EDITOR(3),
    COMPANY_VIEWER(4),
    ACTIVATE_ACCOUNT(5);

    private int code;

    Role(int code) {
        this.code = code;
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
