package yjhb.meeti.domain.calender.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import yjhb.meeti.domain.calender.entity.Calender;

public interface CalenderRepository extends JpaRepository<Calender, Long> {

}
