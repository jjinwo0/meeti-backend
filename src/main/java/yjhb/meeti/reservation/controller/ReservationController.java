package yjhb.meeti.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import yjhb.meeti.reservation.dto.ReservationDTO;
import yjhb.meeti.user.entity.User;
import yjhb.meeti.reservation.service.ReservationService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/res/create")
    public ResponseEntity<?> create(@RequestBody @Valid ReservationDTO dto, User user){

        reservationService.create(dto, user);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
