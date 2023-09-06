package yjhb.meeti.api.office.registration.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yjhb.meeti.api.office.registration.service.OfficeRegService;
import yjhb.meeti.api.office.registration.dto.OfficeRegDto;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.domain.user.service.UserService;
import yjhb.meeti.global.jwt.service.TokenManager;
import yjhb.meeti.global.util.AuthorizationHeaderUtils;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "Office Registration", description = "개인 공유 오피스 등록 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/meeti/reg")
public class OfficeRegController {

    private final TokenManager tokenManager;
    private final OfficeRegService officeRegService;
    private final UserService userService;

    @Tag(name = "Office Registration")
    @PostMapping("/office/{userId}")
    public ResponseEntity<String> registrationOffice(@RequestBody OfficeRegDto officeRegDto,
                                                     @PathVariable("userId") Long userId,
                                                     HttpServletRequest httpServletRequest){
        String authorization = httpServletRequest.getHeader("Authorization");
        tokenManager.validateToken(authorization);

        String accessToken = authorization.split(" ")[1];
        tokenManager.validateToken(accessToken);

        User findUser = userService.findUserByUserId(userId);
        officeRegService.registrationOffice(officeRegDto, findUser);

        return ResponseEntity.ok("Office Registration Success");
    }
}
