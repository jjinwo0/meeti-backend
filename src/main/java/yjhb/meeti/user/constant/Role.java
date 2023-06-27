package yjhb.meeti.user.constant;

public enum Role {

    COMMON, ADMIN;

    public static Role from(String role) {
        return Role.valueOf(role);
    }
}
