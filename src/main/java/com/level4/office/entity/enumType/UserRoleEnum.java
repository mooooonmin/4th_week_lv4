package com.level4.office.entity.enumType;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserRoleEnum {
    USER(Authority.USER),
    ADMIN(Authority.ADMIN),
    GUEST(Authority.GUEST);

    private final String authority;

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String USER = "USER";
        public static final String ADMIN = "ADMIN";
        public static final String GUEST = "GUEST";
    }
}
