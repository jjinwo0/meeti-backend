package yjhb.meeti.api.registration.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.domain.calender.entity.Calender;
import yjhb.meeti.domain.calender.repository.CalenderRepository;
import yjhb.meeti.domain.calender.service.CalenderService;
import yjhb.meeti.domain.office.entity.Office;
import yjhb.meeti.domain.office.repository.OfficeRepository;
import yjhb.meeti.domain.office.service.OfficeService;
import yjhb.meeti.domain.reservation.entity.Reservation;
import yjhb.meeti.domain.reservation.repository.ReservationRepository;
import yjhb.meeti.domain.reservation.service.ReservationService;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.global.jwt.service.TokenManager;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationRegService {

    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;
    private final TokenManager tokenManager;

    public Long createReservation(User user, Office office, Calender calender){

        Reservation reservation = Reservation.builder()
                .user(user)
                .office(office)
                .calender(calender)
                .build();

        reservationRepository.save(reservation);

        return reservation.getId();
    }
}
