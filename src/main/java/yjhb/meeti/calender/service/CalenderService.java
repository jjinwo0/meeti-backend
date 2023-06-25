package yjhb.meeti.calender.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.calender.dto.CalenderDTO;
import yjhb.meeti.calender.entity.Calender;
import yjhb.meeti.user.entity.User;
import yjhb.meeti.calender.repository.CalenderRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CalenderService {

    private final CalenderRepository calenderRepository;

    public List<Calender> findAll(){

        return calenderRepository.findAll();
    }

    public Long createSchedule(CalenderDTO dto, User user){

        Calender cal = Calender.builder()
                .dto(dto)
                .user(user)
                .build();

        calenderRepository.save(cal);

        return cal.getId();
    }

    public void deleteSchedule(Long id){

        Calender findCal = calenderRepository.findById(id).get();
        calenderRepository.delete(findCal);
    }
}
