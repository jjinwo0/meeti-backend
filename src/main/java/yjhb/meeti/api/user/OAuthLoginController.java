package yjhb.meeti.api.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yjhb.meeti.dto.user.OAuthLoginDto;
import yjhb.meeti.service.user.OAuthLoginService;
import yjhb.meeti.global.validate.OAuthValidator;
import yjhb.meeti.domain.user.constant.UserType;
import yjhb.meeti.global.util.AuthorizationHeaderUtils;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "OAuth Login", description = "소셜 로그인 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/meeti/oauth")
public class OAuthLoginController {

    private final OAuthLoginService oAuthLoginService;
    private final OAuthValidator oAuthValidator;

    @Tag(name = "OAuth Login")
    @PostMapping("/login")
    public ResponseEntity<OAuthLoginDto.Response> oauthLogin(@RequestBody OAuthLoginDto.Request oauthLoginRequestDto,
                                                             HttpServletRequest httpServletRequest){

        String authorization = httpServletRequest.getHeader("Authorization");

        // 헤더 필수값 체크
        AuthorizationHeaderUtils.validateAuthorization(authorization);
        oAuthValidator.validateUserType(oauthLoginRequestDto.getUserType());

        String accessToken = authorization.split(" ")[1];
        OAuthLoginDto.Response jwtTokenResponseDto =
                oAuthLoginService.oauthLogin(accessToken, UserType.from(oauthLoginRequestDto.getUserType()));

        return ResponseEntity.ok(jwtTokenResponseDto);
    }
}
