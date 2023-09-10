package yjhb.meeti.api.meeting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.api.meeting.dto.MeetingDto;
import yjhb.meeti.domain.meeting.dto.Meeting;
import yjhb.meeti.domain.meeting.repository.MeetingRepository;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.global.error.ErrorCode;
import yjhb.meeti.global.error.exception.BusinessException;

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

    public Meeting findByMeetingId(Long id){

        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEETING));

        return meeting;
    }

    public void deleteMeeting(Long id){

        meetingRepository.deleteById(id);
    }
}
