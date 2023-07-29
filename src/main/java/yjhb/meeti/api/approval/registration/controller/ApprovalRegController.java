package yjhb.meeti.api.approval.registration.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yjhb.meeti.api.approval.registration.dto.ApprovalRegDto;
import yjhb.meeti.api.approval.registration.service.ApprovalRegService;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.domain.user.service.UserService;
import yjhb.meeti.global.jwt.service.TokenManager;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "Approval Registration", description = "결재 등록 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/meeti/reg")
public class ApprovalRegController {

    private final TokenManager tokenManager;
    private final ApprovalRegService approvalRegService;
    private final UserService userService;

    @Tag(name = "Approval Registration")
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
