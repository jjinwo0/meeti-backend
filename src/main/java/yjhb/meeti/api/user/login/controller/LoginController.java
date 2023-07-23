package yjhb.meeti.api.user.login.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yjhb.meeti.api.user.login.dto.LoginDTO;
import yjhb.meeti.api.user.login.service.LoginService;

@Tag(name = "Login", description = "User Login API") // API에 대한 구체적 설명
@RestController
@RequiredArgsConstructor
@RequestMapping("/meeti/user")
public class LoginController {
    private final LoginService loginService;

    @Tag(name = "Login")
    @Operation(summary = "User Login API", description = "유저 서비스를 활용하기 위한 로그인 서비스 API")
    @PostMapping("/login")
    public ResponseEntity<LoginDTO.Response> login(@RequestBody LoginDTO.Request requestDto){

        LoginDTO.Response jwtTokenResponseDto =
                loginService.login(requestDto.getEmail(), requestDto.getPassword());

        return ResponseEntity.ok(jwtTokenResponseDto);

    }
}
