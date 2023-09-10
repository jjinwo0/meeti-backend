package yjhb.meeti.api.office.search.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yjhb.meeti.api.office.search.dto.OfficeResponseDto;
import yjhb.meeti.domain.office.entity.Office;
import yjhb.meeti.domain.office.service.OfficeService;
import yjhb.meeti.global.jwt.service.TokenManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meeti/office")
public class OfficeSearchController {

    private final TokenManager tokenManager;
    private final OfficeService officeService;

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
}
