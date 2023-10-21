package yjhb.meeti.api.reservation;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yjhb.meeti.dto.reservation.ReservationRegDto;
import yjhb.meeti.dto.reservation.ReservationResponseDto;
import yjhb.meeti.domain.office.Office;
import yjhb.meeti.service.calender.CalenderService;
import yjhb.meeti.service.office.OfficeService;
import yjhb.meeti.domain.reservation.Reservation;
import yjhb.meeti.service.reservation.ReservationService;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.service.user.UserService;
import yjhb.meeti.global.jwt.service.TokenManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Tag(name = "Search Reservation", description = "예약 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/meeti/reservation")
public class ReservationController {

    private final TokenManager tokenManager;
    private final ReservationService reservationService;
    private final UserService userService;
    private final OfficeService officeService;
    private final CalenderService calendarService;

    @Tag(name = "Find Reservation")
    @GetMapping("/search/{reservationId}")
    public ResponseEntity<ReservationResponseDto> findReservation(@PathVariable("reservationId") Long id,
                                                       HttpServletRequest httpServletRequest){

        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        Reservation findReservation = reservationService.findReservationById(id);

        ReservationResponseDto dto = ReservationResponseDto.of(findReservation);

        return ResponseEntity.ok(dto);
    }

    @Tag(name = "Find Reservation by UserId")
    @GetMapping("/search/user/{userId}")
    public ResponseEntity<List> findUserReservation(@PathVariable("userId") Long userId,
                                                HttpServletRequest httpServletRequest){

        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        List<ReservationResponseDto> findReservation = reservationService.findReservationByUserId(userId);

        return ResponseEntity.ok(findReservation);
    }

    @Tag(name = "Find Reservation by Office Id")
    @GetMapping("/search/office/{officeId}")
    public ResponseEntity<List> findOfficeReservation(@PathVariable("officeId") Long officeId,
                                                      HttpServletRequest httpServletRequest){

        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        List<ReservationResponseDto> findReservation = reservationService.findReservationByOfficeId(officeId);

        return ResponseEntity.ok(findReservation);
    }

    @Tag(name = "Find Reservation by Office Id")
    @GetMapping("/search/officeUser/{officeId}")
    public ResponseEntity<List> findReservationByOfficeUser(@PathVariable("officeId") Long userId,
                                                            HttpServletRequest httpServletRequest){

        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        List<ReservationResponseDto> findReservation = reservationService.findReservationByUserId(userId);

        return ResponseEntity.ok(findReservation);
    }

    @Tag(name = "Create Reservation")
    @PostMapping("/reg/{userId}")
    public ResponseEntity<Boolean> createReservation(@RequestBody ReservationRegDto reservationRegDto,
                                                     @PathVariable("userId") Long userId,
                                                     HttpServletRequest httpServletRequest){

        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        User findUser = userService.findUserByUserId(userId);
        Office findOffice = officeService.findOfficeById(reservationRegDto.getOfficeId());

        reservationService.createReservation(findUser, reservationRegDto, findOffice);

        calendarService.registrationCalenderByReservation(reservationRegDto, findOffice, findUser);

        return ResponseEntity.ok(true);
    }

    @Tag(name = "Create Reservation By Officer")
    @PostMapping("/reg/officer/{userId}")
    public ResponseEntity<Boolean> createReservationByOfficeUser(@RequestBody ReservationRegDto reservationRegDto,
                                                                 @PathVariable("userId") Long userId,
                                                                 HttpServletRequest httpServletRequest){

        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        User findUser = userService.findUserByUserId(userId);
        Office findOffice = officeService.findOfficeById(reservationRegDto.getOfficeId());

        reservationService.createReservationByOfficeUser(findUser, reservationRegDto, findOffice);

        calendarService.registrationCalenderByReservation(reservationRegDto, findOffice, findUser);

        return ResponseEntity.ok(true);
    }
}
