package yjhb.meeti.domain.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yjhb.meeti.domain.reservation.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
