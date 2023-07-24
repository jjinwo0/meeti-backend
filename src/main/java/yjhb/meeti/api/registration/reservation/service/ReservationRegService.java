package yjhb.meeti.api.registration.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.api.registration.reservation.dto.ReservationRegDto;
import yjhb.meeti.domain.calender.entity.Calender;
import yjhb.meeti.domain.calender.repository.CalenderRepository;
import yjhb.meeti.domain.calender.service.CalenderService;
import yjhb.meeti.domain.office.entity.Office;
import yjhb.meeti.domain.office.repository.OfficeRepository;
import yjhb.meeti.domain.office.service.OfficeService;
import yjhb.meeti.domain.reservation.entity.Reservation;
import yjhb.meeti.domain.reservation.repository.ReservationRepository;
import yjhb.meeti.domain.user.entity.User;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationRegService {

    private final ReservationRepository reservationRepository;

    public Long createReservation(User user, ReservationRegDto dto, Office office){

        Reservation reservation = Reservation.builder()
                .date(dto.getDate())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .user(user)
                .office(office)
                .build();

        reservationRepository.save(reservation);

        return reservation.getId();
    }
}
