package yjhb.meeti.global.interceptor;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import yjhb.meeti.global.error.ErrorCode;
import yjhb.meeti.global.error.exception.AuthenticationException;
import yjhb.meeti.global.jwt.constant.TokenType;
import yjhb.meeti.global.jwt.service.TokenManager;
import yjhb.meeti.global.util.AuthorizationHeaderUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final TokenManager tokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // preflight 요청 넘기기
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        // 1. Authorization Header 검증
        String authorization = request.getHeader("Authorization");
        AuthorizationHeaderUtils.validateAuthorization(authorization);

        // 2. 토큰 검증
        String accessToken = authorization.split(" ")[1];
        tokenManager.validateToken(accessToken);

        // 3. 토큰 타입
        Claims tokenClaims = tokenManager.getTokenClaims(accessToken);
        String tokenType = tokenClaims.getSubject();
        if (!TokenType.isAccessToken(tokenType))
            throw new AuthenticationException(ErrorCode.NOT_ACCESS_TOKEN_TYPE);

        return true;
    }
}
