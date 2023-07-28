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
import yjhb.meeti.global.error.ErrorCode;
import yjhb.meeti.global.error.exception.BusinessException;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationRegService {

    private final ReservationRepository reservationRepository;

    public void validateReservation(Office office, Reservation reservation){
        List<Reservation> reservations = office.getReservations();

        for (Reservation res : reservations){
            if (reservation.getStartTime().isBefore(res.getEndTime())
                    || reservation.getEndTime().isAfter(res.getStartTime()))
                throw new BusinessException(ErrorCode.ALREADY_EXISTS_RESERVATION);
        }
    }

    public Long createReservation(User user, ReservationRegDto dto, Office office){

        Reservation reservation = Reservation.builder()
                .date(dto.getDate())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .user(user)
                .office(office)
                .build();

        validateReservation(office, reservation);
        office.getReservations().add(reservation);

        reservationRepository.save(reservation);

        return reservation.getId();
    }
}
