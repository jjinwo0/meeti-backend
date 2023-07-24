package yjhb.meeti.api.registration.reservation.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yjhb.meeti.api.registration.calender.dto.CalenderRegDto;
import yjhb.meeti.api.registration.calender.service.CalenderRegService;
import yjhb.meeti.api.registration.office.dto.OfficeRegDto;
import yjhb.meeti.api.registration.office.service.OfficeRegService;
import yjhb.meeti.api.registration.reservation.dto.ReservationRegDto;
import yjhb.meeti.api.registration.reservation.service.ReservationRegService;
import yjhb.meeti.domain.calender.entity.Calender;
import yjhb.meeti.domain.calender.service.CalenderService;
import yjhb.meeti.domain.office.entity.Office;
import yjhb.meeti.domain.office.service.OfficeService;
import yjhb.meeti.domain.reservation.service.ReservationService;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.domain.user.service.UserService;
import yjhb.meeti.global.jwt.service.TokenManager;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "Create Reservation", description = "예약 생성 서비스 API")
@RestController
@RequestMapping("/meeti/reg")
@RequiredArgsConstructor
public class ReservationRegController {

    private final ReservationRegService reservationRegService;
    private final OfficeService officeService;
    private final CalenderService calenderService;
    private final UserService userService;
    private final TokenManager tokenManager;

    @Tag(name = "Create Reservation")
    @PostMapping("/reservation/{userId}")
    public ResponseEntity<String> createReservation(@RequestBody ReservationRegDto reservationRegDto,
                                                    @PathVariable("userId") Long userId,
                                                    HttpServletRequest httpServletRequest){

        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        User findUser = userService.findUserByUserId(userId);
        Office findOffice = officeService.findOfficeById(reservationRegDto.getOfficeId());

        reservationRegService.createReservation(findUser, reservationRegDto, findOffice);

        return ResponseEntity.ok("Create Reservation Successfully");
    }
}
