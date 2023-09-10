package yjhb.meeti.domain.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.api.reservation.search.dto.ReservationResponseDto;
import yjhb.meeti.domain.office.entity.Office;
import yjhb.meeti.domain.office.service.OfficeService;
import yjhb.meeti.domain.reservation.entity.Reservation;
import yjhb.meeti.domain.reservation.repository.ReservationRepository;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.domain.user.service.UserService;
import yjhb.meeti.global.error.ErrorCode;
import yjhb.meeti.global.error.exception.EntityNotFoundException;

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
                    .date(res.getDate())
                    .startTime(res.getStartTime())
                    .endTime(res.getEndTime())
                    .officeName(officeService.findOfficeById(res.getId()).getPlaceName())
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
                    .date(res.getDate())
                    .startTime(res.getStartTime())
                    .endTime(res.getEndTime())
                    .officeName(officeService.findOfficeById(res.getId()).getPlaceName())
                    .build();

            reservationResponseDtos.add(dto);
        }

        return reservationResponseDtos;
    }
}
