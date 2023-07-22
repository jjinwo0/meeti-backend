package yjhb.meeti.api.registration.approval.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yjhb.meeti.api.registration.approval.dto.ApprovalRegDto;
import yjhb.meeti.api.registration.approval.service.ApprovalRegService;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.domain.user.service.UserService;
import yjhb.meeti.global.jwt.service.TokenManager;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meeti/reg")
public class ApprovalRegController {

    private final TokenManager tokenManager;
    private final ApprovalRegService approvalRegService;
    private final UserService userService;

    @PostMapping("/approval/{userId}")
    public ResponseEntity<String> registrationApproval(@PathVariable("userId") Long userId,
                                                               @RequestBody ApprovalRegDto dto,
                                                               HttpServletRequest httpServletRequest){

        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        User findUser = userService.findUserByUserId(userId);

        approvalRegService.regApproval(dto, findUser);

        return ResponseEntity.ok("Approval Registration Successfully");
    }
}
