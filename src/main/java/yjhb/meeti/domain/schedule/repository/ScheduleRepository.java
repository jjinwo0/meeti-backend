package yjhb.meeti.domain.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import yjhb.meeti.domain.schedule.entity.Schedule;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("select s from Schedule s where s.title like %:title%")
    List<Schedule> findByTitle(@Param("title") String title);
}
