package yjhb.meeti.domain.external.oauth.service;

import yjhb.meeti.domain.external.oauth.model.OAuthAttributes;

public interface SocialLoginApiService { // 소셜 플랫폼에서 회원 정보 get

    OAuthAttributes getUserInfo(String accessToken);
}
