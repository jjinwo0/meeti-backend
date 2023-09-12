package yjhb.meeti.api.office.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yjhb.meeti.api.office.dto.OfficeRegDto;
import yjhb.meeti.api.office.dto.OfficeResponseDto;
import yjhb.meeti.domain.office.entity.Office;
import yjhb.meeti.domain.office.service.OfficeService;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.domain.user.service.UserService;
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

        OfficeResponseDto officeDto = OfficeResponseDto.of(findOffice);

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
    @PostMapping("/reg/{userId}")
    public ResponseEntity<Boolean> registrationOffice(@RequestBody OfficeRegDto officeRegDto,
                                                      @PathVariable("userId") Long userId,
                                                      HttpServletRequest httpServletRequest) throws IOException {
        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];
        tokenManager.validateToken(accessToken);

        User findUser = userService.findUserByUserId(userId);
        officeService.registrationOffice(officeRegDto, findUser);

        return ResponseEntity.ok(true);
    }
}
