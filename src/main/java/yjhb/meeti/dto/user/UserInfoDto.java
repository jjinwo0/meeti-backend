package yjhb.meeti.dto.user;

import lombok.Builder;
import lombok.Getter;
import yjhb.meeti.domain.user.constant.Role;
import yjhb.meeti.domain.user.entity.Friend;
import yjhb.meeti.domain.user.entity.User;

@Getter @Builder
public class UserInfoDto {

    private Long id;
    private String username;
    private String profile;
    private Role role;

    @Builder
    public UserInfoDto(Long id, String username, String profile, Role role) {
        this.id = id;
        this.username = username;
        this.profile = profile;
        this.role = role;
    }

    public static UserInfoDto from(User user){

        return UserInfoDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .profile(user.getProfile())
                .role(user.getRole())
                .build();
    }
}
