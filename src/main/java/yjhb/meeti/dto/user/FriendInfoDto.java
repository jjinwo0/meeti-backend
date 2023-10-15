package yjhb.meeti.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FriendInfoDto {

    private Long id;
    private String username;
    private String profile;
    private boolean favorite;
}
