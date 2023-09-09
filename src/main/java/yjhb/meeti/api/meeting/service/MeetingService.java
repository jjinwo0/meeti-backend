package yjhb.meeti.api.meeting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.api.meeting.dto.MeetingDto;
import yjhb.meeti.domain.meeting.dto.Meeting;
import yjhb.meeti.domain.meeting.repository.MeetingRepository;
import yjhb.meeti.domain.user.entity.User;

@Service
@Transactional
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;

    public void register(MeetingDto.Request dto, User user){

        Meeting meeting = Meeting.builder()
                .title(dto.getTitle())
                .detail(dto.getDetail())
                .date(dto.getDate())
                .user(user)
                .build();

        meetingRepository.save(meeting);
    }
}
