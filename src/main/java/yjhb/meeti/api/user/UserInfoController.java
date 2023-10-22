package yjhb.meeti.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.global.resolver.memberinfo.UserInfo;
import yjhb.meeti.dto.user.UserInfoDto;
import yjhb.meeti.service.user.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meeti/user")
public class UserInfoController {

    private final UserService userService;

    @GetMapping("/info/{userId}")
    public ResponseEntity<UserInfoDto> getUserInfo(@PathVariable("userId") Long userId){

        UserInfoDto userInfo = userService.findUserInfo(userId);

        return ResponseEntity.ok(userInfo);
    }

    @GetMapping("/info")
    public ResponseEntity<List> searchUserInfoList(@RequestParam String username){

        List<UserInfoDto> findUserInfoList = userService.findUserInfoByUsername(username);

        return ResponseEntity.ok(findUserInfoList);
    }

    @GetMapping("/info/all/{userId}")
    public ResponseEntity<List> findAll(@PathVariable("userId") Long userId){

        List<UserInfoDto> response = userService.findAll().stream()
                .filter(user -> user.getId() != userId)
                .map(UserInfoDto::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
