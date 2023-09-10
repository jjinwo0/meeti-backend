package yjhb.meeti.api.reservation.registration.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.api.reservation.registration.dto.ReservationRegDto;
import yjhb.meeti.domain.office.entity.Office;
import yjhb.meeti.domain.reservation.entity.Reservation;
import yjhb.meeti.domain.reservation.repository.ReservationRepository;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.global.error.ErrorCode;
import yjhb.meeti.global.error.exception.BusinessException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

        LocalDate date = LocalDate.parse(dto.getDate().substring(0, 10));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startTime = LocalTime.parse(dto.getStartTime(), formatter);
        LocalTime endTime = LocalTime.parse(dto.getEndTime(), formatter);

        Reservation reservation = Reservation.builder()
                .date(date)
                .startTime(startTime)
                .endTime(endTime)
                .user(user)
                .office(office)
                .build();

        validateReservation(office, reservation);
        office.updateStatus(false); //todo: 예약 기간에 따른 상태 변경 로직 구현

        office.getReservations().add(reservation);

        reservationRepository.save(reservation);

        return reservation.getId();
    }
}
