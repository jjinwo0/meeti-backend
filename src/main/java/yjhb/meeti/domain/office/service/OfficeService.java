package yjhb.meeti.domain.office.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.api.office.dto.OfficeRegDto;
import yjhb.meeti.api.office.dto.OfficeResponseDto;
import yjhb.meeti.domain.office.entity.Office;
import yjhb.meeti.domain.office.repository.OfficeRepository;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.domain.user.repository.UserRepository;
import yjhb.meeti.global.error.ErrorCode;
import yjhb.meeti.global.error.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OfficeService {

    private final UserRepository userRepository;
    private final OfficeRepository officeRepository;

    public Office findOfficeById(Long id){
        return officeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_OFFICE));
    }

    public List<OfficeResponseDto> findOfficeByUserId(Long userId){

        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_EXISTS));

        List<Office> offices = findUser.getOffices();
        List<OfficeResponseDto> response = new ArrayList<>();

        for (Office office : offices){
            response.add(
                    OfficeResponseDto.builder()
                            .id(office.getId())
                            .placeName(office.getPlaceName())
                            .pay(office.getPay())
                            .description(office.getDescription())
                            .address(office.getAddress())
                            .addressDetail(office.getDetailAddress())
                            .telNum(office.getTelNum())
                            .image(office.getImage())
                            .status(office.isStatus())
                            .build()
            );
        }

        return response;
    }

    public List<OfficeResponseDto> findOfficeByAddress(String address){

        List<Office> officeList = officeRepository.findByAddressContaining(address);
        List<OfficeResponseDto> response = new ArrayList<>();

        for (Office office : officeList){
            response.add(
                    OfficeResponseDto.builder()
                            .id(office.getId())
                            .placeName(office.getPlaceName())
                            .pay(office.getPay())
                            .description(office.getDescription())
                            .address(office.getAddress())
                            .addressDetail(office.getDetailAddress())
                            .telNum(office.getTelNum())
                            .image(office.getImage())
                            .status(office.isStatus())
                            .build()
            );
        }

        return response;
    }

    public List<OfficeResponseDto> findAllOffice(){

        List<Office> findAll = officeRepository.findAll();
        List<OfficeResponseDto> response = new ArrayList<>();

        for (Office office : findAll){
            response.add(
                    OfficeResponseDto.builder()
                            .id(office.getId())
                            .placeName(office.getPlaceName())
                            .pay(office.getPay())
                            .description(office.getDescription())
                            .address(office.getAddress())
                            .addressDetail(office.getDetailAddress())
                            .telNum(office.getTelNum())
                            .image(office.getImage())
                            .status(office.isStatus())
                            .build()
            );
        }

        return response;
    }

    public List<OfficeResponseDto> findByPlaceName(String placeName){

        List<Office> findOffices = officeRepository.findByPlaceNameContaining(placeName);
        List<OfficeResponseDto> response = new ArrayList<>();

        for (Office office : findOffices){
            response.add(
                    OfficeResponseDto.builder()
                            .id(office.getId())
                            .placeName(office.getPlaceName())
                            .pay(office.getPay())
                            .description(office.getDescription())
                            .address(office.getAddress())
                            .addressDetail(office.getDetailAddress())
                            .telNum(office.getTelNum())
                            .image(office.getImage())
                            .build()
            );
        }

        return response;
    }

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
