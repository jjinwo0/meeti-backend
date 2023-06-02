package yjhb.meeti.service.calender;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.dto.CalenderDTO;
import yjhb.meeti.entity.Calender;
import yjhb.meeti.repository.CalenderRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CalenderService {

    private final CalenderRepository calenderRepository;

    public List<Calender> findAll(){

        return calenderRepository.findAll();
    }

    public Long createSchedule(CalenderDTO dto){

        Calender cal = Calender.builder()
                .dto(dto)
                .build();

        calenderRepository.save(cal);

        return cal.getId();
    }
}
