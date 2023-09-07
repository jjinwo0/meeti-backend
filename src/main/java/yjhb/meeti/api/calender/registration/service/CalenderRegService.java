package yjhb.meeti.api.calender.registration.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.api.calender.registration.dto.CalenderRegDto;
import yjhb.meeti.api.calender.search.dto.CalenderResponseDto;
import yjhb.meeti.domain.calender.entity.Calender;
import yjhb.meeti.domain.calender.repository.CalenderRepository;
import yjhb.meeti.domain.user.entity.User;

@Service
@Transactional
@RequiredArgsConstructor
public class CalenderRegService {

    private final CalenderRepository calenderRepository;

    public CalenderResponseDto registrationCalender(CalenderRegDto calenderRegDto, User user){
        Calender calender = Calender.builder()
                .user(user)
                .title(calenderRegDto.getTitle())
                .color(calenderRegDto.getColor())
                .startDate(calenderRegDto.getStartDate().substring(0, 10))
                .startTime(calenderRegDto.getStartTime().substring(11, 19))
                .endDate(calenderRegDto.getEndDate().substring(0, 10))
                .endTime(calenderRegDto.getEndTime().substring(11, 19))
                .build();

        calenderRepository.save(calender);

        return CalenderResponseDto.builder().build();
    }
}
