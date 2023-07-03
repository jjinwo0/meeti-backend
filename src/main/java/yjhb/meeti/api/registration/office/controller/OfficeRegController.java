package yjhb.meeti.api.registration.office.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yjhb.meeti.api.registration.office.dto.OfficeRegDto;
import yjhb.meeti.api.registration.office.service.OfficeRegService;
import yjhb.meeti.global.jwt.service.TokenManager;
import yjhb.meeti.global.util.AuthorizationHeaderUtils;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meeti/reg")
public class OfficeRegController {

    private final TokenManager tokenManager;
    private final OfficeRegService officeRegService;

    @PostMapping("/office")
    public ResponseEntity<String> registrationOffice(@RequestBody OfficeRegDto officeRegDto,
                                                     HttpServletRequest httpServletRequest){
        String authorization = httpServletRequest.getHeader("Authorization");
        AuthorizationHeaderUtils.validateAuthorization(authorization);

        String accessToken = authorization.split(" ")[1];
        tokenManager.validateToken(accessToken);

        officeRegService.registrationOffice(officeRegDto);

        return ResponseEntity.ok("Registration Success");
    }
}
