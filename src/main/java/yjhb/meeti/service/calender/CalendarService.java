package yjhb.meeti.service.calender;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.domain.calender.Calendar;
import yjhb.meeti.dto.calender.CalendarRegDto;
import yjhb.meeti.dto.calender.CalendarResponseDto;
import yjhb.meeti.repository.calender.CalendarRepository;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.repository.user.UserRepository;
import yjhb.meeti.global.error.ErrorCode;
import yjhb.meeti.global.error.exception.BusinessException;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;
    private final UserRepository userRepository;

    public Calendar findCalenderById(Long id){
        return calendarRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_CALENDER));
    }

    public List<CalendarResponseDto> findCalenderByUserId(Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_EXISTS));

        List<Calendar> calenders = findUser.getCalenders();
        List<CalendarResponseDto> response = new ArrayList<>();
        for (Calendar cal : calenders){
            response.add(
                    CalendarResponseDto.builder()
                            .id(cal.getId())
                            .title(cal.getTitle())
                            .color(cal.getColor())
                            .start(cal.getStart())
                            .initTime(cal.getInitTime())
                            .end(cal.getEnd())
                            .finishTime(cal.getFinishTime())
                            .place(cal.getPlace())
                            .build()
            );
        }

        return response;
    }

    public CalendarResponseDto registrationCalender(CalendarRegDto calendarRegDto, User user){

        String startDate = calendarRegDto.getStart().substring(0, 10);
        String endDate = calendarRegDto.getEnd().substring(0, 10);

        String start = startDate.substring(0, 8) + (Integer.parseInt(startDate.substring(8, 10)) + 1 < 10 ?
                "0" + (Integer.parseInt(startDate.substring(8, 10)) + 1) : (Integer.parseInt(startDate.substring(8, 10)) + 1));

        String end = endDate.substring(0, 8) + (Integer.parseInt(endDate.substring(8, 10)) + 2 < 10 ?
                "0" + (Integer.parseInt(endDate.substring(8, 10)) + 2) : (Integer.parseInt(endDate.substring(8, 10)) + 2));

        Calendar calender = Calendar.builder()
                .user(user)
                .title(calendarRegDto.getTitle())
                .color(calendarRegDto.getColor())
                .start(start)
                .initTime(calendarRegDto.getInitTime())
                .end(end)
                .finishTime(calendarRegDto.getFinishTime())
                .place(calendarRegDto.getPlace())
                .build();

        calendarRepository.save(calender);

        return CalendarResponseDto.builder().build();
    }

    @Transactional
    public void deleteCalender(Long id){

        calendarRepository.deleteById(id);
    }
}
