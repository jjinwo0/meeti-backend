package yjhb.meeti.api.registration.calender.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yjhb.meeti.api.registration.calender.dto.CalenderRegDto;
import yjhb.meeti.api.registration.calender.dto.CalenderResponseDto;
import yjhb.meeti.api.registration.calender.service.CalenderRegService;
import yjhb.meeti.domain.calender.entity.Calender;
import yjhb.meeti.domain.calender.service.CalenderService;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.domain.user.repository.UserRepository;
import yjhb.meeti.domain.user.service.UserService;
import yjhb.meeti.global.error.ErrorCode;
import yjhb.meeti.global.error.exception.EntityNotFoundException;
import yjhb.meeti.global.jwt.service.TokenManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meeti/calender")
public class CalenderController {

    private final CalenderRegService calenderRegService;
    private final CalenderService calenderService;
    private final UserRepository userRepository;
    private final TokenManager tokenManager;

    @PostMapping("/reg/{userId}")
    public ResponseEntity<String> registrationCalender(@RequestBody CalenderRegDto calenderRegDto,
                                                       @PathVariable("userId") Long userId,
                                                       HttpServletRequest httpServletRequest){
        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_EXISTS));
        calenderRegService.registrationCalender(calenderRegDto, findUser);

        return ResponseEntity.ok("Registration Success");
    }

    @GetMapping("/find/{userId}")
    public ResponseEntity<List> findCalender(@PathVariable("userId") Long userId,
                                                            HttpServletRequest httpServletRequest){
        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);
        List<CalenderResponseDto> calenders = calenderService.findCalenderByUserId(userId);

        return ResponseEntity.ok(calenders);
    }
}
