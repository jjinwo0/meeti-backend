package yjhb.meeti.calender.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import yjhb.meeti.calender.dto.CalenderDTO;
import yjhb.meeti.calender.entity.Calender;
import yjhb.meeti.user.entity.User;
import yjhb.meeti.user.service.UserService;
import yjhb.meeti.calender.service.CalenderService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Log4j2
public class CalenderController {

    private final UserService userService;
    private final CalenderService calenderService;

    @PostMapping("/calender/create")
    public ResponseEntity<Calender> createSchedule(@RequestBody @Valid CalenderDTO dto, HttpSession session){

        User loginUser = (User) session.getAttribute("loginUser");

        calenderService.createSchedule(dto, loginUser);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
