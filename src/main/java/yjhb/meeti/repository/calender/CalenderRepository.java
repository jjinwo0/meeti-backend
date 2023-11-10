package yjhb.meeti.repository.calender;

import org.springframework.data.jpa.repository.JpaRepository;
import yjhb.meeti.domain.calender.Calender;
import yjhb.meeti.domain.user.entity.User;

public interface CalenderRepository extends JpaRepository<Calender, Long> {

    Calender findByUserAndPlace(User user, String title);
}
