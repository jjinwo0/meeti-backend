package yjhb.meeti.api.search.calender.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yjhb.meeti.api.search.calender.dto.CalenderResponseDto;
import yjhb.meeti.domain.calender.service.CalenderService;
import yjhb.meeti.global.jwt.service.TokenManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("meeti/calender")
public class CalenderSearchController {

    private final TokenManager tokenManager;
    private final CalenderService calenderService;


    @GetMapping("/search/{userId}")
    public ResponseEntity<List> findMyCalender(@PathVariable("userId") Long userId,
                                             HttpServletRequest httpServletRequest){
        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);
        List<CalenderResponseDto> calenders = calenderService.findCalenderByUserId(userId);

        return ResponseEntity.ok(calenders);
    }
}
