package yjhb.meeti.service.calender;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.dto.calender.CalenderRegDto;
import yjhb.meeti.dto.calender.CalenderResponseDto;
import yjhb.meeti.domain.calender.Calender;
import yjhb.meeti.repository.calender.CalenderRepository;
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
public class CalenderService {

    private final CalenderRepository calenderRepository;
    private final UserRepository userRepository;

    public void createCalender(Calender calender){
        calenderRepository.save(calender);
    }

    public Calender findCalenderById(Long id){
        return calenderRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_CALENDER));
    }

    public List<CalenderResponseDto> findCalenderByUserId(Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_EXISTS));

        List<Calender> calenders = findUser.getCalenders();
        List<CalenderResponseDto> response = new ArrayList<>();
        for (Calender cal : calenders){
            response.add(
                    CalenderResponseDto.builder()
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

    public CalenderResponseDto registrationCalender(CalenderRegDto calenderRegDto, User user){

        String startDate = calenderRegDto.getStart().substring(0, 10);
        String endDate = calenderRegDto.getEnd().substring(0, 10);

        String start = startDate.substring(0, 8) + (Integer.parseInt(startDate.substring(8, 10)) + 1 < 10 ?
                "0" + (Integer.parseInt(startDate.substring(8, 10)) + 1) : (Integer.parseInt(startDate.substring(8, 10)) + 1));

        String end = endDate.substring(0, 8) + (Integer.parseInt(endDate.substring(8, 10)) + 2 < 10 ?
                "0" + (Integer.parseInt(endDate.substring(8, 10)) + 2) : (Integer.parseInt(endDate.substring(8, 10)) + 2));

        Calender calender = Calender.builder()
                .user(user)
                .title(calenderRegDto.getTitle())
                .color(calenderRegDto.getColor())
                .start(start)
                .initTime(calenderRegDto.getInitTime())
                .end(end)
                .finishTime(calenderRegDto.getFinishTime())
                .place(calenderRegDto.getPlace())
                .build();

        calenderRepository.save(calender);

        return CalenderResponseDto.builder().build();
    }

    @Transactional
    public void deleteCalender(Long id){

        calenderRepository.deleteById(id);
    }
}
