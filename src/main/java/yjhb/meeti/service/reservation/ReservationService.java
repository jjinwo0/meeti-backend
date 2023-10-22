package yjhb.meeti.service.reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.domain.approval.constant.Decision;
import yjhb.meeti.domain.reservation.Status;
import yjhb.meeti.domain.user.constant.Role;
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
import java.util.stream.Collectors;

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

        return findReservation.stream()
                .map(ReservationResponseDto::of)
                .collect(Collectors.toList());
    }

    public List<ReservationResponseDto> findReservationByUserId(Long id){

        User findUser = userService.findUserByUserId(id);

        List<Reservation> findReservation = findUser.getReservations();

        return findReservation.stream()
                .map(ReservationResponseDto::of)
                .collect(Collectors.toList());
    }

    // 대기 상태 예약 리스트
    public List<ReservationResponseDto> findWaitReservationByUserId(Long id){

        User findUser = userService.findUserByUserId(id);

        List<Reservation> findReservation = findUser.getReservations();

        return findReservation.stream()
                .map(ReservationResponseDto::of)
                .filter(reservationResponseDto -> findReservationById(reservationResponseDto.getId()).getStatus().equals(Status.WAIT))
                .collect(Collectors.toList());
    }

    public void validateReservation(Office office, Reservation reservation){
        List<Reservation> reservations = office.getReservations();

        for (Reservation res : reservations){
            if (reservation.getStartTime().isBefore(res.getEndTime())
                    || reservation.getEndTime().isAfter(res.getStartTime()))
                throw new BusinessException(ErrorCode.ALREADY_EXISTS_RESERVATION);
        }
    }

    @Transactional
    public Long createReservation(User user, ReservationRegDto dto, Office office){

        LocalDate date = LocalDate.parse(dto.getDate().substring(0, 10));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startTime = LocalTime.parse(dto.getStartTime(), formatter);
        LocalTime endTime = LocalTime.parse(dto.getEndTime(), formatter);

        Reservation reservation;

        if(user.getRole().equals(Role.ADMIN_OFFICE) || user.getRole().equals(Role.ADMIN_OFFICE)){
            reservation = Reservation.builder()
                    .date(date)
                    .startTime(startTime)
                    .endTime(endTime)
                    .user(user)
                    .office(office)
                    .status(Status.WAIT)
                    .build();
        }else {
            reservation = Reservation.builder()
                    .date(date)
                    .startTime(startTime)
                    .endTime(endTime)
                    .user(user)
                    .office(office)
                    .status(Status.CONFIRM)
                    .build();
        }

        validateReservation(office, reservation);
        office.updateStatus(false); //todo: 예약 기간에 따른 상태 변경 로직 구현

        office.getReservations().add(reservation);

        reservationRepository.save(reservation);

        return reservation.getId();
    }

    @Transactional
    public Status updateReservationStatus(String decision, Long reservationId){

        Reservation findReservation = findReservationById(reservationId);

        if (decision.equals(Decision.REJECT.toString()))
            findReservation.updateStatus(Status.REJECT);
        else if (decision.equals(Decision.CONFIRM.toString()))
            findReservation.updateStatus(Status.CONFIRM);

        return findReservation.getStatus();
    }

    public Reservation findReservationByPlaceNameNameAndUserId(String placeName, Long userId){

        User findUser = userService.findUserByUserId(userId);
        Office findOffice = officeService.findOneByPlaceName(placeName);

        return reservationRepository.findByOfficeAndUser(findOffice, findUser);
    }
}
