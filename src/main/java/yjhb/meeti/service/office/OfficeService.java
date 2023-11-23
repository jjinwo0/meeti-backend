package yjhb.meeti.service.office;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import yjhb.meeti.dto.office.OfficeRegDto;
import yjhb.meeti.dto.office.OfficeResponseDto;
import yjhb.meeti.domain.office.Office;
import yjhb.meeti.repository.office.OfficeRepository;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.repository.user.UserRepository;
import yjhb.meeti.global.error.ErrorCode;
import yjhb.meeti.global.error.exception.EntityNotFoundException;
import yjhb.meeti.service.file.S3Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OfficeService {

    private final UserRepository userRepository;
    private final OfficeRepository officeRepository;
    private final S3Service s3Service;

    /**
     * 식별자를 통한 오피스 검색
     * @param id
     * @return Office Entity
     */
    public Office findOfficeById(Long id){
        return officeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_OFFICE));
    }

    /**
     * User가 등록한 Office 검색
     * @param userId
     * @return List
     */
    public List<OfficeResponseDto> findOfficeByUserId(Long userId){

        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_EXISTS));

        List<Office> offices = findUser.getOffices();

        List<OfficeResponseDto> response = offices.stream()
                .map(OfficeResponseDto::from)
                .collect(Collectors.toList());

        return response;
    }

    /**
     * 주소로 Office 검색
     * @param address
     * @return List
     */
    public List<OfficeResponseDto> findOfficeByAddress(String address){

        List<Office> officeList = officeRepository.findByAddressContaining(address);

        List<OfficeResponseDto> response = officeList.stream()
                .map(OfficeResponseDto::from)
                .collect(Collectors.toList());

        return response;
    }

    /**
     * 모든 Office 검색
     * @return List
     */
    public List<OfficeResponseDto> findAllOffice(){

        List<Office> findAll = officeRepository.findAll();

        List<OfficeResponseDto> response = findAll.stream()
                .map(OfficeResponseDto::from)
                .collect(Collectors.toList());

        return response;
    }

    /**
     * Office 이름으로 단일 검색
     * @param placeName
     * @return Office
     */
    public Office findOneByPlaceName(String placeName){

        List<Office> findOffice = officeRepository.findByPlaceNameContaining(placeName);

        if (findOffice.isEmpty())
            throw new EntityNotFoundException(ErrorCode.NOT_FOUND_OFFICE);

        return findOffice.get(0);
    }

    /**
     * Office 등록
     * @param officeRegDto
     * @param image
     * @param user
     * @return Office 식별자
     * @throws IOException
     */
    public Long registrationOffice(OfficeRegDto officeRegDto, MultipartFile image, User user) throws IOException {

        String officeImage = s3Service.upload(image, "officeImage");

        Office office = Office.builder()
                .user(user)
                .placeName(officeRegDto.getPlaceName())
                .image(officeImage)
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
