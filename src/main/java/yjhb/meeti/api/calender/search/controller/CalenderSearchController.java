package yjhb.meeti.api.calender.search.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yjhb.meeti.api.calender.search.dto.CalenderResponseDto;
import yjhb.meeti.domain.calender.service.CalenderService;
import yjhb.meeti.global.jwt.service.TokenManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Tag(name = "Search Calender", description = "스케쥴 검색 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("meeti/calender")
public class CalenderSearchController {

    private final TokenManager tokenManager;
    private final CalenderService calenderService;


    @Tag(name = "Search Calender")
    @GetMapping("/search/{userId}")
    public ResponseEntity<List> findMyCalender(@PathVariable("userId") Long userId,
                                             HttpServletRequest httpServletRequest){
        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);
        List<CalenderResponseDto> calenders = calenderService.findCalenderByUserId(userId);

        return ResponseEntity.ok(calenders);
    }

    @Tag(name = "Delete Calender")
    @DeleteMapping("/delete/{calenderId}")
    public ResponseEntity<Boolean> deleteCalender(@PathVariable("calenderId") Long id,
                                                  HttpServletRequest httpServletRequest){

        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        calenderService.deleteCalender(id);

        return ResponseEntity.ok(true);
    }
}
