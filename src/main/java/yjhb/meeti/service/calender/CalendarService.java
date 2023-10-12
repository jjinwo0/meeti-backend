package yjhb.meeti.service.calender;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.domain.calender.Calendar;
import yjhb.meeti.domain.office.Office;
import yjhb.meeti.dto.calender.CalendarRegDto;
import yjhb.meeti.dto.calender.CalenderResponseDto;
import yjhb.meeti.dto.meeting.MeetingDto;
import yjhb.meeti.dto.reservation.ReservationRegDto;
import yjhb.meeti.global.error.exception.AuthenticationException;
import yjhb.meeti.repository.calender.CalendarRepository;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.repository.user.UserRepository;
import yjhb.meeti.global.error.ErrorCode;
import yjhb.meeti.global.error.exception.BusinessException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<CalenderResponseDto> findCalenderByUserId(Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_EXISTS));

        List<Calendar> calenders = findUser.getCalenders();
        List<CalenderResponseDto> response = calenders.stream()
                .map(CalenderResponseDto::from)
                .collect(Collectors.toList());

        return response;
    }

    @Transactional
    public CalenderResponseDto registrationCalender(CalendarRegDto calendarRegDto, User user){

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

        return CalenderResponseDto.builder().build();
    }

    @Transactional
    public CalenderResponseDto registrationCalenderByMeeting(MeetingDto.Request meetingDto, User user){

        Calendar calender = Calendar.builder()
                .user(user)
                .title(meetingDto.getTitle())
//                .color(calendarRegDto.getColor()) // todo 색상 추가
                .start(LocalDate.now().toString())
//                .initTime() // todo 시간 처리 추가
                .end(LocalDate.now().toString())
//                .finishTime() // todo 시간 처리 추가
//                .place() // todo 장소 추가
                .build();

        calendarRepository.save(calender);

        return CalenderResponseDto.builder().build();
    }

    @Transactional
    public CalenderResponseDto registrationCalenderByReservation(ReservationRegDto dto, Office office, User user){

        // todo Office 규격 수정

        Calendar calender = Calendar.builder()
                .user(user)
                .title(office.getPlaceName())
                .color("#548ff7")
                .start(LocalDate.now().toString())
                .initTime(dto.getStartTime())
                .end(LocalDate.now().toString())
                .finishTime(dto.getEndTime())
                .place(office.getPlaceName())
                .build();

        calendarRepository.save(calender);

        return CalenderResponseDto.builder().build();
    }

    @Transactional
    public Boolean updateCalendar(Calendar calendar, CalendarRegDto dto, User user){

        if (!user.getCalenders().contains(calendar))
            throw new AuthenticationException(ErrorCode.NOT_VALID_CALENDAR);

        String startDate = dto.getStart().substring(0, 10);
        String endDate = dto.getEnd().substring(0, 10);

        String start = startDate.substring(0, 8) + (Integer.parseInt(startDate.substring(8, 10)) + 1 < 10 ?
                "0" + (Integer.parseInt(startDate.substring(8, 10)) + 1) : (Integer.parseInt(startDate.substring(8, 10)) + 1));

        String end = endDate.substring(0, 8) + (Integer.parseInt(endDate.substring(8, 10)) + 2 < 10 ?
                "0" + (Integer.parseInt(endDate.substring(8, 10)) + 2) : (Integer.parseInt(endDate.substring(8, 10)) + 2));

        calendar.update(dto.getTitle(), dto.getColor(), start, dto.getInitTime(), end, dto.getFinishTime(), dto.getPlace(), user);

        return true;
    }

    @Transactional
    public void deleteCalender(Long id){

        calendarRepository.deleteById(id);
    }
}
