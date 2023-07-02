package yjhb.meeti.domain.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yjhb.meeti.domain.schedule.entity.Schedule;
import yjhb.meeti.domain.schedule.service.ScheduleService;
import yjhb.meeti.global.jwt.service.TokenManager;
import yjhb.meeti.global.util.AuthorizationHeaderUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meeti/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final TokenManager tokenManager;

    @PostMapping("/{title}")
    public ResponseEntity<List<Schedule>> findScheduleByTitle(@PathVariable("title") @RequestBody String title,
                                                        HttpServletRequest httpServletRequest){
        String authorization = httpServletRequest.getHeader("Authorization");
        AuthorizationHeaderUtils.validateAuthorization(authorization);

        String accessToken = authorization.split(" ")[1];
        tokenManager.validateToken(accessToken);

        List<Schedule> findByTitle = scheduleService.findByTitle(title);

        return ResponseEntity.ok(findByTitle);
    }

}
