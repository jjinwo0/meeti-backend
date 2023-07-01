package yjhb.meeti.api.join.dto;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class UserJoinDTO {

    private String email;
    private String password;
    private String username;
    private String profile;
}
