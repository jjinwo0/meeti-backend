package yjhb.meeti.domain.calender.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.api.search.calender.dto.CalenderResponseDto;
import yjhb.meeti.domain.calender.entity.Calender;
import yjhb.meeti.domain.calender.repository.CalenderRepository;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.domain.user.repository.UserRepository;
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

    public void createSchedule(Calender calender){
        calenderRepository.save(calender);
    }

    public Calender findCalenderById(Long id){
        return calenderRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_CALENDER));
    }

    public List<CalenderResponseDto> findCalenderByUserId(Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_EXISTS));

        List<Calender> calenders = findUser.getCalenders();
        List<CalenderResponseDto> response = new ArrayList<>();
        for (Calender cal : calenders){
            response.add(
                    CalenderResponseDto.builder()
                            .title(cal.getTitle())
                            .color(cal.getColor())
                            .start(cal.getStart())
                            .end(cal.getEnd())
                            .build()
            );
        }

        return response;
    }
}
