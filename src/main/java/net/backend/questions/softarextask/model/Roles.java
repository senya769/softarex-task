package net.backend.questions.softarextask.model;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
    ADMIN_ROLE("ADMIN_ROLE"),
    USER_ROLE("USER_ROLE");

    private final String value;

    Roles(String value) {
        this.value = value;
    }

    @Override
    public String getAuthority() {
        return value;
    }
}
