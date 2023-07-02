package yjhb.meeti.api.login.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/meeti")
public class LoginController {

    private final Validator validator;
    private final LoginService loginService;
    private final UserService userService;


    @PostMapping("/login")
    public ResponseEntity<LoginDTO.Response> login(@RequestBody LoginDTO.Request requestDto){

        LoginDTO.Response jwtTokenResponseDto =
                loginService.login(requestDto.getEmail(), requestDto.getPassword());

        return ResponseEntity.ok(jwtTokenResponseDto);

    }
}
