package yjhb.meeti.domain.external.oauth.model;

import lombok.Builder;
import lombok.Getter;
import yjhb.meeti.domain.user.constant.Role;
import yjhb.meeti.domain.user.constant.UserType;
import yjhb.meeti.domain.user.entity.User;

@Getter @Builder
public class OAuthAttributes {

    private String name;
    private String email;
    private String profile;
    private UserType userType;

    public User toUserEntity(UserType userType, Role role){
        return User.builder()
                .userType(userType)
                .username(name)
                .email(email)
                .profile(profile)
                .role(role)
                .build();
    }
}
