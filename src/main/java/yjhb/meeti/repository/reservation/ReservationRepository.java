package yjhb.meeti.repository.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import yjhb.meeti.domain.reservation.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
