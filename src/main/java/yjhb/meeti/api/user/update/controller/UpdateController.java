package yjhb.meeti.api.user.update.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yjhb.meeti.api.user.update.dto.UpdateDTO;
import yjhb.meeti.api.user.update.service.UpdateService;
import yjhb.meeti.global.jwt.service.TokenManager;
import yjhb.meeti.global.resolver.memberinfo.UserInfo;
import yjhb.meeti.global.resolver.memberinfo.UserInfoDto;
import yjhb.meeti.global.util.AuthorizationHeaderUtils;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meeti/user")
public class UpdateController {

    private final UpdateService updateService;
    private final TokenManager tokenManager;

    @PostMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody UpdateDTO updateDTO,
                                                @UserInfo UserInfoDto userInfoDto,
                                                HttpServletRequest httpServletRequest){

        String authorization = httpServletRequest.getHeader("Authorization");
        AuthorizationHeaderUtils.validateAuthorization(authorization);

        String accessToken = authorization.split(" ")[1];
        // 토큰 유효성 체크
        tokenManager.validateToken(accessToken);

        updateService.updateUser(userInfoDto.getId(), updateDTO.getUsername(), updateDTO.getProfile());

        return ResponseEntity.ok("Update Success");
    }
}
