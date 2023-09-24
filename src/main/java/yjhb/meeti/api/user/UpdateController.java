package yjhb.meeti.api.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yjhb.meeti.dto.user.UpdateDTO;
import yjhb.meeti.service.user.UpdateService;
import yjhb.meeti.global.jwt.service.TokenManager;
import yjhb.meeti.global.resolver.memberinfo.UserInfo;
import yjhb.meeti.global.resolver.memberinfo.UserInfoDto;
import yjhb.meeti.global.util.AuthorizationHeaderUtils;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "Update", description = "유저 정보 update API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/meeti/user")
public class UpdateController {

    private final UpdateService updateService;
    private final TokenManager tokenManager;

    @Tag(name = "Update User")
    @PostMapping("/update")
    public ResponseEntity<Boolean> updateUser(@RequestBody UpdateDTO updateDTO,
                                                @UserInfo UserInfoDto userInfoDto,
                                                HttpServletRequest httpServletRequest){

        String authorization = httpServletRequest.getHeader("Authorization");
        AuthorizationHeaderUtils.validateAuthorization(authorization);

        String accessToken = authorization.split(" ")[1];
        // 토큰 유효성 체크
        tokenManager.validateToken(accessToken);

        updateService.updateUser(userInfoDto.getId(), updateDTO.getUsername(), updateDTO.getProfile());

        return ResponseEntity.ok(true);
    }
}
