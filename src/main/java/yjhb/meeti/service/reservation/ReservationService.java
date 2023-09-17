package yjhb.meeti.service.reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.dto.reservation.ReservationRegDto;
import yjhb.meeti.dto.reservation.ReservationResponseDto;
import yjhb.meeti.domain.office.Office;
import yjhb.meeti.domain.reservation.Reservation;
import yjhb.meeti.repository.reservation.ReservationRepository;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.global.error.ErrorCode;
import yjhb.meeti.global.error.exception.BusinessException;
import yjhb.meeti.global.error.exception.EntityNotFoundException;
import yjhb.meeti.service.user.UserService;
import yjhb.meeti.service.office.OfficeService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final OfficeService officeService;
    private final UserService userService;

    public Reservation findReservationById(Long id){
        Reservation findReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_RESERVATION));

        return findReservation;
    }

    public List<ReservationResponseDto> findReservationByOfficeId(Long id){

        Office findOffice = officeService.findOfficeById(id);

        List<Reservation> findReservation = findOffice.getReservations();
        List<ReservationResponseDto> reservationResponseDtos = new ArrayList<>();

        for (Reservation res : findReservation){

            ReservationResponseDto dto = ReservationResponseDto.builder()
                    .id(res.getId())
                    .date(res.getDate())
                    .startTime(res.getStartTime())
                    .endTime(res.getEndTime())
                    .officeName(officeService.findOfficeById(res.getId()).getPlaceName())
                    .telNum(officeService.findOfficeById(res.getId()).getTelNum())
                    .image(officeService.findOfficeById(res.getId()).getImage())
                    .build();

            reservationResponseDtos.add(dto);
        }

        return reservationResponseDtos;
    }

    public List<ReservationResponseDto> findReservationByUserId(Long id){

        User findUser = userService.findUserByUserId(id);

        List<Reservation> findReservation = findUser.getReservations();
        List<ReservationResponseDto> reservationResponseDtos = new ArrayList<>();

        for (Reservation res : findReservation){

            ReservationResponseDto dto = ReservationResponseDto.builder()
                    .id(res.getId())
                    .date(res.getDate())
                    .startTime(res.getStartTime())
                    .endTime(res.getEndTime())
                    .officeName(officeService.findOfficeById(res.getId()).getPlaceName())
                    .telNum(officeService.findOfficeById(res.getId()).getTelNum())
                    .image(officeService.findOfficeById(res.getId()).getImage())
                    .build();

            reservationResponseDtos.add(dto);
        }

        return reservationResponseDtos;
    }

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
