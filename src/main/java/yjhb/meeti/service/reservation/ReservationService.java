package yjhb.meeti.service.reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yjhb.meeti.dto.ReservationDTO;
import yjhb.meeti.entity.Reservation;
import yjhb.meeti.entity.User;
import yjhb.meeti.repository.ReservationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;


    public List<Reservation> findAll(){
        return reservationRepository.findAll();
    }

    public Long create(ReservationDTO dto, User user){

        Reservation reservation = Reservation.builder()
                .dto(dto)
                .user(user)
                .build();

        reservationRepository.save(reservation);

        return reservation.getId();
    }
}
