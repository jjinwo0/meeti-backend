package yjhb.meeti.api.registration.calender.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.api.registration.calender.dto.CalenderRegDto;
import yjhb.meeti.api.search.calender.dto.CalenderResponseDto;
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
                .start(calenderRegDto.getStart())
                .end(calenderRegDto.getEnd())
                .build();

        calenderRepository.save(calender);

        return CalenderResponseDto.builder().build();
    }
}
