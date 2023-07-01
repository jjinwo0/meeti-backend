package yjhb.meeti.api.token.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.api.token.dto.AccessTokenResponseDTO;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.domain.user.service.UserService;
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
        String accessToken = tokenManager.createAccessToken(findUser.getId(), findUser.getRole(), accessTokenExpireTime);

        return AccessTokenResponseDTO.builder()
                .grantType(GrantType.BEARER.getType())
                .accessToken(accessToken)
                .accessTokenExpireTime(accessTokenExpireTime)
                .build();
    }
}
