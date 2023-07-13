package yjhb.meeti.api.registration.office.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yjhb.meeti.api.registration.office.dto.OfficeRegDto;
import yjhb.meeti.api.registration.office.service.OfficeRegService;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.domain.user.service.UserService;
import yjhb.meeti.global.jwt.service.TokenManager;
import yjhb.meeti.global.util.AuthorizationHeaderUtils;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meeti/reg")
public class OfficeRegController {

    private final TokenManager tokenManager;
    private final OfficeRegService officeRegService;
    private final UserService userService;

    @PostMapping("/office/{userId}")
    public ResponseEntity<String> registrationOffice(@RequestBody OfficeRegDto officeRegDto,
                                                     @PathVariable("userId") Long userId,
                                                     HttpServletRequest httpServletRequest){
        String authorization = httpServletRequest.getHeader("Authorization");
        AuthorizationHeaderUtils.validateAuthorization(authorization);

        String accessToken = authorization.split(" ")[1];
        tokenManager.validateToken(accessToken);

        User findUser = userService.findUserByUserId(userId);
        officeRegService.registrationOffice(officeRegDto, findUser);

        return ResponseEntity.ok("Office Registration Success");
    }
}
