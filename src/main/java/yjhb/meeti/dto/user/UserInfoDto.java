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
}
