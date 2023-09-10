package yjhb.meeti.api.meeting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yjhb.meeti.api.meeting.dto.MeetingDto;
import yjhb.meeti.api.meeting.service.MeetingService;
import yjhb.meeti.domain.meeting.dto.Meeting;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.domain.user.service.UserService;
import yjhb.meeti.global.jwt.service.TokenManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("meeti/meeting")
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService meetingService;

    private final UserService userService;

    private final TokenManager tokenManager;

    @PostMapping("/reg/{userId}")
    public ResponseEntity<Boolean> createMeeting(@RequestBody MeetingDto.Request dto,
                                                 @PathVariable("userId")Long userId,
                                                 HttpServletRequest request){

        String authorization = request.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        User findUser = userService.findUserByUserId(userId);

        meetingService.register(dto, findUser);

        return ResponseEntity.ok(true);
    }

    @GetMapping("/search/{userId}")
    public ResponseEntity<List> searchMeetingByUserId(@PathVariable("userId") Long userId,
                                                      HttpServletRequest request){

        String authorization = request.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        List<Meeting> meetings = userService.findUserByUserId(userId).getMeetings();

        return ResponseEntity.ok(meetings);
    }

    @GetMapping("/search/{meetingId}")
    public ResponseEntity<Meeting> searchMeetingById(@PathVariable("meetingId") Long id,
                                                      HttpServletRequest request){

        String authorization = request.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        Meeting findMeeting = meetingService.findByMeetingId(id);

        return ResponseEntity.ok(findMeeting);
    }

    @PostMapping("/delete/{meetingId}")
    public ResponseEntity<Boolean> deleteMeetingByMeetingId(@PathVariable("meetingId") Long id,
                                                            HttpServletRequest request){

        String authorization = request.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        meetingService.deleteMeeting(id);

        return ResponseEntity.ok(true);
    }
}
