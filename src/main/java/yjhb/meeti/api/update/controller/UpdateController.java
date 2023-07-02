package yjhb.meeti.api.update.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yjhb.meeti.api.update.dto.UpdateDTO;
import yjhb.meeti.api.update.service.UpdateService;
import yjhb.meeti.global.resolver.memberinfo.UserInfo;
import yjhb.meeti.global.resolver.memberinfo.UserInfoDto;
import yjhb.meeti.global.util.AuthorizationHeaderUtils;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UpdateController {

    private final UpdateService updateService;

    @PostMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody UpdateDTO updateDTO,
                                                @UserInfo UserInfoDto userInfoDto,
                                                HttpServletRequest httpServletRequest){

        String authorization = httpServletRequest.getHeader("Authorization");
        AuthorizationHeaderUtils.validateAuthorization(authorization);

        updateService.updateUser(userInfoDto.getId(), updateDTO.getUsername(), updateDTO.getProfile());

        return ResponseEntity.ok("Update Success");
    }
}
