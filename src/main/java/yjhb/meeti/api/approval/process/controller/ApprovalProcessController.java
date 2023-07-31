package yjhb.meeti.api.approval.process.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yjhb.meeti.api.approval.process.service.ApprovalProcessService;
import yjhb.meeti.domain.approval.constant.Decision;
import yjhb.meeti.global.interceptor.AdminAuthorizationInterceptor;
import yjhb.meeti.global.jwt.service.TokenManager;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/meeti/admin/process")
@RequiredArgsConstructor
public class ApprovalProcessController {

    private final TokenManager tokenManager;
    private final ApprovalProcessService approvalProcessService;

    @PostMapping("/approval/{approvalId}")
    public ResponseEntity<String> process(@PathVariable("approvalId") Long approvalId,
                                     @RequestBody Decision decision,
                                     HttpServletRequest httpServletRequest) {

        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        approvalProcessService.proceed(approvalId, decision);

        return ResponseEntity.ok("Approval is " + decision.toString());
    }
}
