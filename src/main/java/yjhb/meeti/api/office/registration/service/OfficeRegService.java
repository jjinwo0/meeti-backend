package yjhb.meeti.api.office.registration.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.api.office.registration.dto.OfficeRegDto;
import yjhb.meeti.domain.office.entity.Office;
import yjhb.meeti.domain.office.repository.OfficeRepository;
import yjhb.meeti.domain.user.entity.User;

@Service
@RequiredArgsConstructor
@Transactional
public class OfficeRegService {

    private final OfficeRepository officeRepository;

    public Long registrationOffice(OfficeRegDto officeRegDto, User user){
        Office office = Office.builder()
                .user(user)
                .placeName(officeRegDto.getPlaceName())
                .image(officeRegDto.getImage())
                .telNum(officeRegDto.getTelNum())
                .pay(officeRegDto.getPay())
                .description(officeRegDto.getDescription())
                .address(officeRegDto.getAddress())
                .detailAddress(officeRegDto.getDetailAddress())
                .status(true)
                .build();

        officeRepository.save(office);

        return office.getId();
    }
}
