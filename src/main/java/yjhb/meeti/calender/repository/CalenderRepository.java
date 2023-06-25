package yjhb.meeti.calender.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yjhb.meeti.calender.entity.Calender;

public interface CalenderRepository extends JpaRepository<Calender, Long> {
    
}
