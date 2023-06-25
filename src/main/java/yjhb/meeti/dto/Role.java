package yjhb.meeti.dto;

public enum Role {

    USER, ADMIN;

    public static Role from(String role) {
        return Role.valueOf(role);
    }
}
