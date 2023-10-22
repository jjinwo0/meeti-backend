package yjhb.meeti.repository.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import yjhb.meeti.domain.office.Office;
import yjhb.meeti.domain.reservation.Reservation;
import yjhb.meeti.domain.user.entity.User;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Reservation findByOfficeAndUser(Office office, User user);

}
