package yjhb.meeti.domain.user.controller;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.domain.user.service.UserService;
import yjhb.meeti.global.jwt.service.TokenManager;
import yjhb.meeti.global.resolver.memberinfo.UserInfo;
import yjhb.meeti.global.resolver.memberinfo.UserInfoDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final TokenManager tokenManager;
    private final UserService userService;

    @GetMapping("/info")
    public ResponseEntity<UserInfoDto> getUserInfo(@UserInfo UserInfoDto userInfoDto){
        Long id = userInfoDto.getId();

        UserInfoDto userInfo = userService.findUserInfo(id);

        return ResponseEntity.ok(userInfo);
    }
}
