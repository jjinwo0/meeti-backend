package yjhb.meeti.service.meeting;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.dto.meeting.MeetingDto;
import yjhb.meeti.domain.meeting.Meeting;
import yjhb.meeti.repository.meeting.MeetingRepository;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.service.user.UserService;
import yjhb.meeti.global.error.ErrorCode;
import yjhb.meeti.global.error.exception.BusinessException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;

    private final UserService userService;

    public void register(MeetingDto.Request dto, User user){

        Meeting meeting = Meeting.builder()
                .title(dto.getTitle())
                .detail(dto.getDetail())
                .date(LocalDate.now())
                .user(user)
                .build();

        meetingRepository.save(meeting);
    }

    public void update(MeetingDto.Request dto, Meeting meeting, User user){

        meeting.update(dto.getDetail(), LocalDate.now(), user);
    }

    public List findMeetingByUserId(Long userId){

        User findUser = userService.findUserByUserId(userId);

        List<Meeting> meetings = findUser.getMeetings();
        List<MeetingDto.Response> response = new ArrayList<>();

        for (Meeting meet : meetings){

            response.add(
                    MeetingDto.Response.builder()
                            .id(meet.getId())
                            .username(meet.getUser().getUsername())
                            .title(meet.getTitle())
                            .detail(meet.getDetail())
                            .date(meet.getDate())
                            .build()
            );
        }

        return response;
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
