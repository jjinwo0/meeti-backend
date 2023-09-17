package yjhb.meeti.api.token;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yjhb.meeti.dto.token.AccessTokenResponseDTO;
import yjhb.meeti.service.token.TokenService;
import yjhb.meeti.global.util.AuthorizationHeaderUtils;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meeti")
public class TokenController {

    private final TokenService tokenService;

    @Tag(name = "Create Access Token")
    @PostMapping("/access-token/issue")
    public ResponseEntity<AccessTokenResponseDTO> createAccessToken(HttpServletRequest httpServletRequest){
        String authorization = httpServletRequest.getHeader("Authorization");
        AuthorizationHeaderUtils.validateAuthorization(authorization);

        String refreshToken = authorization.split(" ")[1];

        AccessTokenResponseDTO accessTokenResponseDTO = tokenService.createAccessTokenByRefreshToken(refreshToken);

        return ResponseEntity.ok(accessTokenResponseDTO);
    }
}
