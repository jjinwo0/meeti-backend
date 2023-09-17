package yjhb.meeti.domain.external.oauth.kakao.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import yjhb.meeti.domain.external.oauth.kakao.client.KakaoUserInfoClient;
import yjhb.meeti.domain.external.oauth.kakao.dto.KakaoUserInfoResponseDto;
import yjhb.meeti.domain.external.oauth.model.OAuthAttributes;
import yjhb.meeti.domain.external.oauth.service.SocialLoginApiService;
import yjhb.meeti.domain.user.constant.UserType;
import yjhb.meeti.global.jwt.constant.GrantType;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class KakaoLoginApiServiceImpl implements SocialLoginApiService {

    private final KakaoUserInfoClient kakaoUserInfoClient;
    private final String CONTENT_TYPE = "application/w-xxx-form-urlencoded;charset=utf-8";

    @Override
    public OAuthAttributes getUserInfo(String accessToken) {
        // AccessToken의 헤더에 BEARER 담기
        // User 정보 조회
        KakaoUserInfoResponseDto kakaoUserInfoResponseDto =
                kakaoUserInfoClient.getKakaoUserInfo(CONTENT_TYPE, GrantType.BEARER.getType()+" "+accessToken);

        // 조회한 User 정보에서 email 정보 추출
        KakaoUserInfoResponseDto.KakaoAccount kakaoAccount = kakaoUserInfoResponseDto.getKakaoAccount();
        String email = kakaoAccount.getEmail();

        // email은 필수값이 아니기 때문에, 담겼는지 먼저 체크
        // email이 없다면 id를 담도록 설계
        return OAuthAttributes.builder()
                .email(!StringUtils.hasText(email) ? kakaoUserInfoResponseDto.getId() : email)
                .name(kakaoAccount.getProfile().getNickname())
                .profile(kakaoAccount.getProfile().getThumbnailImageUrl())
                .userType(UserType.KAKAO)
                .build();
    }
}
