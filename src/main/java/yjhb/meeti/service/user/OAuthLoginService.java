package yjhb.meeti.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.dto.user.OAuthLoginDto;
import yjhb.meeti.domain.external.oauth.model.OAuthAttributes;
import yjhb.meeti.domain.external.oauth.service.SocialLoginApiService;
import yjhb.meeti.domain.external.oauth.service.SocialLoginApiServiceFactory;
import yjhb.meeti.domain.user.constant.Role;
import yjhb.meeti.domain.user.constant.UserType;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.global.jwt.dto.JwtTokenDto;
import yjhb.meeti.global.jwt.service.TokenManager;

import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class OAuthLoginService {

    private final UserService userService;
    private final TokenManager tokenManager;

    public OAuthLoginDto.Response oauthLogin(String accessToken, UserType userType){

        SocialLoginApiService socialLoginApiService = SocialLoginApiServiceFactory.getSocialLoginApiService(userType);
        OAuthAttributes userInfo = socialLoginApiService.getUserInfo(accessToken);// OAuthAttributes.builder()
        log.info("userInfo : {}", userInfo);

        JwtTokenDto jwtTokenDto;

        Optional<User> findUser = userService.findUserByEmail(userInfo.getEmail());
        if (findUser.isEmpty()){ // 신규 회원
            // OAuthAttributes 객체를 활용하여 엔티티 생성
            User oauthUser = userInfo.toUserEntity(userType, Role.COMMON);
            // 등록
            userService.registerUser(oauthUser);

            jwtTokenDto = tokenManager.createJwtTokenDto(oauthUser.getId(), oauthUser.getRole());
            oauthUser.updateToken(jwtTokenDto);
        }else{ // 기존 회원
            // 토큰 생성
            User oauthUser = findUser.get();

            jwtTokenDto = tokenManager.createJwtTokenDto(oauthUser.getId(), oauthUser.getRole());
            oauthUser.updateToken(jwtTokenDto);
        }

        return OAuthLoginDto.Response.of(jwtTokenDto);
    }
}
