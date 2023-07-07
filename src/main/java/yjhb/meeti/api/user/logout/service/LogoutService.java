package yjhb.meeti.api.user.logout.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.domain.user.service.UserService;
import yjhb.meeti.global.error.ErrorCode;
import yjhb.meeti.global.error.exception.AuthenticationException;
import yjhb.meeti.global.jwt.constant.TokenType;
import yjhb.meeti.global.jwt.service.TokenManager;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class LogoutService {

    private final UserService userService;
    private final TokenManager tokenManager;

    public void logout(String accessToken){

        // 1. 토큰 검증
        tokenManager.validateToken(accessToken);

        // 2. 토큰 타입 검증
        Claims tokenClaims = tokenManager.getTokenClaims(accessToken);
        String tokenType = tokenClaims.getSubject();
        if (!TokenType.isAccessToken(tokenType))
            throw new AuthenticationException(ErrorCode.NOT_ACCESS_TOKEN_TYPE);

        // 3. 토큰 만료 처리
        Long id = Long.valueOf((Integer) tokenClaims.get("id"));
        User findUser = userService.findUserByUserId(id);
        findUser.expireRefreshToken(LocalDateTime.now()); // 토큰 만료 시간을 현재 시간으로 update
    }
}
