package yjhb.meeti.api.reservation.search.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yjhb.meeti.api.reservation.search.dto.ReservationResponseDto;
import yjhb.meeti.domain.office.service.OfficeService;
import yjhb.meeti.domain.reservation.entity.Reservation;
import yjhb.meeti.domain.reservation.service.ReservationService;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.domain.user.service.UserService;
import yjhb.meeti.global.jwt.service.TokenManager;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meeti/reservation")
public class ReservationSearchController {

    private final TokenManager tokenManager;
    private final UserService userService;
    private final OfficeService officeService;
    private final ReservationService reservationService;

    @GetMapping("/search/{userId}")
    public ResponseEntity<List> findReservation(@PathVariable("userId") Long userId,
                                                HttpServletRequest httpServletRequest){

        User findUser = userService.findUserByUserId(userId);
        List<Reservation> reservations = findUser.getReservations();
        List<ReservationResponseDto> reservationResponseDtos = new ArrayList<>();

        for (Reservation res : reservations){

            ReservationResponseDto dto = ReservationResponseDto.builder()
                    .date(res.getDate())
                    .startTime(res.getStartTime())
                    .endTime(res.getEndTime())
                    .officeName(officeService.findOfficeById(res.getId()).getPlaceName())
                    .build();

            reservationResponseDtos.add(dto);
        }

        return ResponseEntity.ok(reservationResponseDtos);
    }
}
