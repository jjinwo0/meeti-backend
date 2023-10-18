package yjhb.meeti.api.approval;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yjhb.meeti.domain.approval.constant.Decision;
import yjhb.meeti.domain.approval.entity.Approval;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.service.approval.ApprovalService;
import yjhb.meeti.global.jwt.service.TokenManager;
import yjhb.meeti.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/meeti/admin/approval")
@RequiredArgsConstructor
@Tag(name = "Approval Process", description = "결재 진행 API")
public class ApprovalAdminController {

    private final TokenManager tokenManager;
    private final ApprovalService approvalService;
    private final UserService userService;

    @Tag(name = "Approval Process")
    @PostMapping("/{userId}/{approvalId}")
    public ResponseEntity<String> process(@PathVariable("userId") Long userId,
                                          @PathVariable("approvalId") Long approvalId,
                                          @RequestBody Decision decision,
                                          HttpServletRequest httpServletRequest) {

        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        userService.validOfficeAdmin(userId);

        approvalService.proceed(approvalId, decision);

        return ResponseEntity.ok("Approval is " + decision.toString());
    }

    @Schema(name = "Approval List")
    @GetMapping(value = "/approval/list/{userId}")
    public ResponseEntity<List> findApprovalList(@PathVariable("userId") Long userId){

        List<Approval> list = approvalService.approvalListForAdmin(userId);

        return ResponseEntity.ok(list);
    }
}
