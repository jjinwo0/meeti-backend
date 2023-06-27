package yjhb.meeti.api.login.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.api.login.dto.LoginDTO;
import yjhb.meeti.global.jwt.dto.JwtTokenDto;
import yjhb.meeti.global.jwt.service.TokenManager;
import yjhb.meeti.user.entity.User;
import yjhb.meeti.user.service.UserService;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class LoginService {

    private final UserService userService;
    private final TokenManager tokenManager;

    public LoginDTO.Response login(String accessToken, String username, String password){

        User findUser = userService.findByPasswordWithUsername(username, password);
        log.info("user : {}", findUser.getId());

        JwtTokenDto jwtTokenDto;

        jwtTokenDto = tokenManager.createJwtTokenDto(findUser.getId(), findUser.getRole());
        findUser.updateRefreshToken(jwtTokenDto);

        return LoginDTO.Response.of(jwtTokenDto);
    }
}
