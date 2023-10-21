package yjhb.meeti.api.office;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yjhb.meeti.dto.office.OfficeRegDto;
import yjhb.meeti.dto.office.OfficeResponseDto;
import yjhb.meeti.domain.office.Office;
import yjhb.meeti.service.office.OfficeService;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.service.user.UserService;
import yjhb.meeti.global.jwt.service.TokenManager;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meeti/office")
public class OfficeController {

    private final TokenManager tokenManager;
    private final OfficeService officeService;
    private final UserService userService;

    @GetMapping("/search/user/{userId}")
    public ResponseEntity<List> findMyOffice(@PathVariable("userId") Long userId,
                                             HttpServletRequest httpServletRequest){
        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);
        List<OfficeResponseDto> responseDto = officeService.findOfficeByUserId(userId);

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/search/{officeId}")
    public ResponseEntity<OfficeResponseDto> findOffice(@PathVariable("officeId") Long id,
                                             HttpServletRequest httpServletRequest){
        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);
        Office findOffice = officeService.findOfficeById(id);

        OfficeResponseDto officeDto = OfficeResponseDto.from(findOffice);

        return ResponseEntity.ok(officeDto);
    }

    @GetMapping("/search/address/{address}")
    public ResponseEntity<List> findOfficeByAddress(@PathVariable("address") String address,
                                                    HttpServletRequest httpServletRequest){
        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);
        List<OfficeResponseDto> findOfficeList = officeService.findOfficeByAddress(address);

        return ResponseEntity.ok(findOfficeList);
    }

    @GetMapping("/search/place/{placeName}")
    public ResponseEntity<List> findOfficeByPlaceName(@PathVariable("placeName") String placeName,
                                                    HttpServletRequest httpServletRequest){
        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);
        List<OfficeResponseDto> findOfficeList = officeService.findByPlaceName(placeName);

        return ResponseEntity.ok(findOfficeList);
    }

    @GetMapping("/search")
    public ResponseEntity<List> findAllOffice(HttpServletRequest httpServletRequest){
        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        List<OfficeResponseDto> allOffice = officeService.findAllOffice();

        return ResponseEntity.ok(allOffice);
    }

    @Tag(name = "Office Registration")
    @PostMapping(value = "/reg/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Boolean> registrationOffice(@RequestPart("officeRegDto") OfficeRegDto officeRegDto,
                                                      @RequestPart("image") MultipartFile image,
                                                      @PathVariable("userId") Long userId,
                                                      HttpServletRequest httpServletRequest) throws IOException {
        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];
        tokenManager.validateToken(accessToken);

        User findUser = userService.findUserByUserId(userId);
        officeService.registrationOffice(officeRegDto, image, findUser);

        return ResponseEntity.ok(true);
    }
}
