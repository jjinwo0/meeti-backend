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
}
