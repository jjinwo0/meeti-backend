package yjhb.meeti.api.registration.office.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.api.registration.office.dto.OfficeRegDto;
import yjhb.meeti.domain.office.entity.Office;
import yjhb.meeti.domain.office.repository.OfficeRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class OfficeRegService {

    private final OfficeRepository officeRepository;

    public Long registrationOffice(OfficeRegDto officeRegDto){
        Office office = Office.builder()
                .placeName(officeRegDto.getPlaceName())
                .image(officeRegDto.getImage())
                .telNum(officeRegDto.getTelNum())
                .pay(officeRegDto.getPay())
                .description(officeRegDto.getDescription())
                .address(officeRegDto.getAddress())
                .detailAddress(officeRegDto.getDetailAddress())
                .build();

        officeRepository.save(office);

        return office.getId();
    }
}
