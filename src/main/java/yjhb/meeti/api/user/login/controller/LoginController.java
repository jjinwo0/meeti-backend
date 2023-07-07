package yjhb.meeti.api.user.login.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yjhb.meeti.api.user.login.dto.LoginDTO;
import yjhb.meeti.api.user.login.service.LoginService;
import yjhb.meeti.api.user.login.validate.Validator;
import yjhb.meeti.domain.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meeti/user")
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
