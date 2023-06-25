package yjhb.meeti.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yjhb.meeti.reservation.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {


}
