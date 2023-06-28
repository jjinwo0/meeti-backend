package yjhb.meeti.domain.external.oauth.service;

import org.springframework.stereotype.Service;
import yjhb.meeti.domain.user.constant.UserType;

import java.util.Map;

@Service
public class SocialLoginApiServiceFactory {

    // SocialLoginApiService 구현체가 각각 들어가게 됨
    private static Map<String, SocialLoginApiService> socialLoginApiServices;

    public SocialLoginApiServiceFactory(Map<String, SocialLoginApiService> socialLoginApiServices) {
        this.socialLoginApiServices = socialLoginApiServices;
    }

    public static SocialLoginApiService getSocialLoginApiService(UserType userType){
        String socialLoginApiServiceBeanName = "";

        if (UserType.KAKAO.equals(userType)){
            socialLoginApiServiceBeanName = "kakaoLoginApiServiceImpl";
        }

        // 빈의 이름으로 구현체 추출
        return socialLoginApiServices.get(socialLoginApiServiceBeanName);
    }
}
