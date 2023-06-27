package yjhb.meeti.api.login.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import yjhb.meeti.api.login.dto.LoginDTO;
import yjhb.meeti.api.login.service.LoginService;
import yjhb.meeti.api.login.validate.Validator;
import yjhb.meeti.domain.user.service.UserService;
import yjhb.meeti.global.util.AuthorizationHeaderUtils;
import yjhb.meeti.domain.user.constant.UserType;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final Validator validator;
    private final LoginService loginService;
    private final UserService userService;


    @PostMapping("/login")
    public ResponseEntity<LoginDTO.Response> login(@RequestBody LoginDTO.Request requestDto,
                                                   HttpServletRequest httpServletRequest){

        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        // 헤더 내부 필수값 체크 (GrantType)
        AuthorizationHeaderUtils.validateAuthorization(authorizationHeader);
        UserType userType = userService.findUserTypeByUsernameAndPassword(requestDto.getEmail(), requestDto.getPassword());
        validator.validateMemberType(userType.toString());

        String accessToken = authorizationHeader.split(" ")[1];

        LoginDTO.Response jwtTokenResponseDto =
                loginService.login(accessToken, requestDto.getEmail(), requestDto.getPassword());

        return ResponseEntity.ok(jwtTokenResponseDto);

    }
}
