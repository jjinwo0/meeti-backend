package yjhb.meeti.api.calender;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yjhb.meeti.domain.calender.Calendar;
import yjhb.meeti.dto.calender.CalendarRegDto;
import yjhb.meeti.dto.calender.CalendarResponseDto;
import yjhb.meeti.service.calender.CalendarService;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.service.user.UserService;
import yjhb.meeti.global.jwt.service.TokenManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Tag(name = "Calendar", description = "스케쥴 검색, 등록 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("meeti/calendar")
public class CalendarController {

    private final TokenManager tokenManager;
    private final CalendarService calendarService;
    private final UserService userService;


    @Tag(name = "Search Calendar")
    @GetMapping("/search/{userId}")
    public ResponseEntity<List> findMyCalendar(@PathVariable("userId") Long userId,
                                             HttpServletRequest httpServletRequest){
        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);
        List<CalendarResponseDto> calenders = calendarService.findCalenderByUserId(userId);

        return ResponseEntity.ok(calenders);
    }

    @Tag(name = "Delete Calendar")
    @DeleteMapping("/delete/{calendarId}")
    public ResponseEntity<Boolean> deleteCalender(@PathVariable("calendarId") Long id,
                                                  HttpServletRequest httpServletRequest){

        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        calendarService.deleteCalender(id);

        return ResponseEntity.ok(true);
    }

    @Tag(name = "Calendar Registration")
    @PostMapping("/reg/{userId}")
    public ResponseEntity<Boolean> registrationCalender(@RequestBody CalendarRegDto calendarRegDto,
                                                        @PathVariable("userId") Long userId,
                                                        HttpServletRequest httpServletRequest){
        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        User findUser = userService.findUserByUserId(userId);
        calendarService.registrationCalender(calendarRegDto, findUser);

        return ResponseEntity.ok(true);
    }

    @Tag(name = "Update Calendar")
    @PostMapping("/reg/{userId}/{calendarId}")
    public ResponseEntity<Boolean> updateCalender(@RequestBody CalendarRegDto calendarRegDto,
                                                  @PathVariable("userId") Long userId,
                                                  @PathVariable("calendarId") Long calendarId,
                                                        HttpServletRequest httpServletRequest){
        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        User findUser = userService.findUserByUserId(userId);
        Calendar finaCalendar = calendarService.findCalenderById(calendarId);
        calendarService.updateCalendar(finaCalendar, calendarRegDto, findUser);

        return ResponseEntity.ok(true);
    }
}
