package yjhb.meeti.global.config.web;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import yjhb.meeti.global.interceptor.AdminAuthorizationInterceptor;
import yjhb.meeti.global.interceptor.AuthenticationInterceptor;
import yjhb.meeti.global.resolver.memberinfo.UserInfoArgumentResolver;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    // accessToken 검증
    private final AuthenticationInterceptor authenticationInterceptor;
    // UserInfo 조회
    private final UserInfoArgumentResolver userInfoArgumentResolver;
    // Role ADMIN 체크하는 인터셉터
    private final AdminAuthorizationInterceptor adminAuthorizationInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedOrigins("*", "http://localhost:8082")
                .allowedMethods(
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.PATCH.name(),
                        HttpMethod.DELETE.name(),
                        HttpMethod.OPTIONS.name()
                ).maxAge(3600); // Access-Control-Max-Age: 3600 으로 설정
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor) // accessToken 검증 우선
                .order(1)
                .addPathPatterns("/meeti/**")
                .excludePathPatterns(
                        "/meeti/user/join",
                        "/meeti/user/join/office",
                        "/meeti/user/join/office/admin",
                        "/meeti/user/login",
                        "/meeti/oauth/login",
                        "/meeti/access-token/issue",
                        "/meeti/user/logout",
                        "/meeti/user/delete/**",
                        "/meeti/user/valid/**",
                        "/kakao/**"
                );

        registry.addInterceptor(adminAuthorizationInterceptor)
                .order(2)
                .addPathPatterns("/meeti/admin/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {

        resolvers.add(userInfoArgumentResolver); // 유저 정보 탐색 리졸버 등록
    }
}
