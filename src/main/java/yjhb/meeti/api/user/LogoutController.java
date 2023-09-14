package yjhb.meeti.api.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yjhb.meeti.service.user.LogoutService;
import yjhb.meeti.global.util.AuthorizationHeaderUtils;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "Logout", description = "로그아웃 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/meeti/user")
public class LogoutController {

    private final LogoutService logoutService;

    @Tag(name = "Logout")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest httpServletRequest){
        String authorization = httpServletRequest.getHeader("Authorization");
        AuthorizationHeaderUtils.validateAuthorization(authorization);

        String accessToken = authorization.split(" ")[1];
        logoutService.logout(accessToken);

        return ResponseEntity.ok("Logout Success");
    }
}
