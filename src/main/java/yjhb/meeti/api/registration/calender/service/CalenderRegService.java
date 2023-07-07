package yjhb.meeti.api.registration.calender.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.api.registration.calender.dto.CalenderRegDto;
import yjhb.meeti.domain.calender.entity.Calender;
import yjhb.meeti.domain.calender.repository.CalenderRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class CalenderRegService {

    private final CalenderRepository calenderRepository;

    public Long registrationCalender(CalenderRegDto calenderRegDto){
        Calender calender = Calender.builder()
                .title(calenderRegDto.getTitle())
                .color(calenderRegDto.getColor())
                .start(calenderRegDto.getStart())
                .end(calenderRegDto.getEnd())
                .build();

        calenderRepository.save(calender);

        return calender.getId();
    }
}
