package yjhb.meeti.repository.meeting;

import org.springframework.data.jpa.repository.JpaRepository;
import yjhb.meeti.domain.meeting.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}
