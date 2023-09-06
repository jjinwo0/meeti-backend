package yjhb.meeti.api.calender.registration.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yjhb.meeti.api.calender.registration.dto.CalenderRegDto;
import yjhb.meeti.api.calender.registration.service.CalenderRegService;
import yjhb.meeti.domain.calender.service.CalenderService;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.domain.user.service.UserService;
import yjhb.meeti.global.jwt.service.TokenManager;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "Calender Registration", description = "스케쥴 등록 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/meeti/reg")
public class CalenderRegController {

    private final CalenderRegService calenderRegService;
    private final CalenderService calenderService;
    private final UserService userService;
    private final TokenManager tokenManager;

    @Tag(name = "Calender Registration")
    @PostMapping("/calender/{userId}")
    public ResponseEntity<Boolean> registrationCalender(@RequestBody CalenderRegDto calenderRegDto,
                                                       @PathVariable("userId") Long userId,
                                                       HttpServletRequest httpServletRequest){
        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        User findUser = userService.findUserByUserId(userId);
        calenderRegService.registrationCalender(calenderRegDto, findUser);

        return ResponseEntity.ok(true);
//        return ResponseEntity.ok("Calender Registration Success");
    }
}
