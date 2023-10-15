package yjhb.meeti.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yjhb.meeti.global.resolver.memberinfo.UserInfo;
import yjhb.meeti.dto.user.UserInfoDto;
import yjhb.meeti.service.user.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meeti/user")
public class UserInfoController {

    private final UserService userService;

    @GetMapping("/info")
    public ResponseEntity<UserInfoDto> getUserInfo(@UserInfo UserInfoDto userInfoDto){
        Long id = userInfoDto.getId();

        UserInfoDto userInfo = userService.findUserInfo(id);

        return ResponseEntity.ok(userInfo);
    }
}
