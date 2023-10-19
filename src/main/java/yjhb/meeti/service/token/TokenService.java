package yjhb.meeti.service.token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.dto.token.AccessTokenResponseDTO;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.service.user.UserService;
import yjhb.meeti.global.jwt.constant.GrantType;
import yjhb.meeti.global.jwt.service.TokenManager;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional
public class TokenService {

    private final UserService userService;
    private final TokenManager tokenManager;

    public AccessTokenResponseDTO createAccessTokenByRefreshToken(String refreshToken){

        User findUser = userService.findUserByRefreshToken(refreshToken);

        Date accessTokenExpireTime = tokenManager.createAccessTokenExpireTime();
        String accessToken = tokenManager.createAccessToken(findUser.getId(), findUser.getUsername(), findUser.getRole(), accessTokenExpireTime);

        return AccessTokenResponseDTO.builder()
                .grantType(GrantType.BEARER.getType())
                .accessToken(accessToken)
                .accessTokenExpireTime(accessTokenExpireTime)
                .build();
    }
}
