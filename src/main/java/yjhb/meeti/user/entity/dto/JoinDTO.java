package yjhb.meeti.user.entity.dto;

import lombok.Data;
import yjhb.meeti.user.constant.Role;
import yjhb.meeti.user.constant.UserType;

@Data
public class JoinDTO {
    private String username;
    private String password;
    private String email;
    private String profile;
    private Role role;
}
