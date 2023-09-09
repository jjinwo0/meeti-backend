package yjhb.meeti.domain.meeting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yjhb.meeti.domain.meeting.dto.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}
