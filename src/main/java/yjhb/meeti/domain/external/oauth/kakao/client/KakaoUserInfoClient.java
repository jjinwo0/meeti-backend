package yjhb.meeti.domain.external.oauth.kakao.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import yjhb.meeti.domain.external.oauth.kakao.dto.KakaoUserInfoResponseDto;

@FeignClient(url = "https://kapi.kakao.com", name = "kakaoUserInfoClient")
public interface KakaoUserInfoClient {

    @GetMapping(value = "/v2/user/me", consumes = "application/json")
    KakaoUserInfoResponseDto getKakaoUserInfo(@RequestHeader("Content-Type") String contentType,
                                              @RequestHeader("Authorization") String accessToken);
}
