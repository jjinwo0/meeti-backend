package yjhb.meeti.domain.user.constant;

public enum Role {

    COMMON, COMMON_OFFICE, ADMIN, ADMIN_OFFICE;

    public static Role from(String role) {
        return Role.valueOf(role);
    }
}
