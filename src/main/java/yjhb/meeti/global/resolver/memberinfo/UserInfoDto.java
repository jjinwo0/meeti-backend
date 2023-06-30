package yjhb.meeti.global.resolver.memberinfo;

import lombok.Builder;
import lombok.Getter;
import yjhb.meeti.domain.user.constant.Role;

@Getter @Builder
public class UserInfoDto {

    private Long id;
    private Role role;
}
