package yjhb.meeti.api.user.join.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class UserJoinDTO {

    private String email;
    private String password;
    private String username;
    private String profile;
}
