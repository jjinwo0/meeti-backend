package yjhb.meeti.api.meeting;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yjhb.meeti.dto.meeting.MeetingDto;
import yjhb.meeti.service.meeting.MeetingService;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.service.user.UserService;
import yjhb.meeti.global.jwt.service.TokenManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Tag(name = "Meeting", description = "회의록 API")
@RestController
@RequestMapping("meeti/meeting")
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService meetingService;

    private final UserService userService;

    private final TokenManager tokenManager;

    @Schema(name = "Register Meeting", description = "회의록 등록 API")
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

    @Schema(name = "Search Meeting", description = "회의록 검색 API (회원 ID)")
    @GetMapping("/search/{userId}")
    public ResponseEntity<List> searchMeetingByUserId(@PathVariable("userId") Long userId,
                                                      HttpServletRequest request){

        String authorization = request.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        List findMeetings = meetingService.findMeetingByUserId(userId);

        return ResponseEntity.ok(findMeetings);
    }

    @Schema(name = "Delete Meeting", description = "회의록 삭제 API (회의록 ID)")
    @DeleteMapping("/delete/{meetingId}")
    public ResponseEntity<Boolean> deleteMeetingByMeetingId(@PathVariable("meetingId") Long id,
                                                            HttpServletRequest request){

        String authorization = request.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        meetingService.deleteMeeting(id);

        return ResponseEntity.ok(true);
    }

    @Schema(name = "Delete All Meeting", description = "회의록 삭제 API (회원의 전체 회의록)")
    @DeleteMapping("/delete/user/{userId}")
    public ResponseEntity<Boolean> deleteAllMeetingByUserId(@PathVariable("userId") Long userId,
                                                            HttpServletRequest request){

        String authorization = request.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        User findUser = userService.findUserByUserId(userId);

        findUser.getMeetings().clear();

        return ResponseEntity.ok(true);
    }
}
