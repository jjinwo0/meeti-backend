package yjhb.meeti.api.registration.calender.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yjhb.meeti.api.registration.calender.dto.CalenderRegDto;
import yjhb.meeti.api.registration.calender.service.CalenderRegService;
import yjhb.meeti.global.jwt.service.TokenManager;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meeti/calender")
public class CalenderController {

    private final CalenderRegService calenderRegService;
    private final TokenManager tokenManager;

    public ResponseEntity<String> registrationCalender(@RequestBody CalenderRegDto calenderRegDto,
                                                       HttpServletRequest httpServletRequest){
        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);
        calenderRegService.registrationCalender(calenderRegDto);

        return ResponseEntity.ok("Registration Success");
    }
}
