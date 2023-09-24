package yjhb.meeti.repository.calender;

import org.springframework.data.jpa.repository.JpaRepository;
import yjhb.meeti.domain.calender.Calendar;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {

}
