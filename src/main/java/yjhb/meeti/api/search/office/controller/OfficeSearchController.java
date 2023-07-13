package yjhb.meeti.api.search.office.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yjhb.meeti.api.search.office.dto.OfficeResponseDto;
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

    @GetMapping("/search/{userId}")
    public ResponseEntity<List> findMyOffice(@PathVariable("userId") Long userId,
                                             HttpServletRequest httpServletRequest){
        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);
        List<OfficeResponseDto> responseDto = officeService.findOfficeByUserId(userId);

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/search/{address}")
    public ResponseEntity<List> findOfficeByAddress(@PathVariable("address") String address,
                                                    HttpServletRequest httpServletRequest){
        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);
        List<OfficeResponseDto> findOfficeList = officeService.findOfficeByAddress(address);

        return ResponseEntity.ok(findOfficeList);
    }
}
