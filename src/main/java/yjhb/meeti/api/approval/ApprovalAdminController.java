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
import yjhb.meeti.dto.approval.ApprovalDto;
import yjhb.meeti.service.approval.ApprovalService;
import yjhb.meeti.global.jwt.service.TokenManager;
import yjhb.meeti.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * Admin Decision
     */
    @Schema(name = "Admin Decision")
    @PostMapping(value = "/decision/{approvalId}")
    public ResponseEntity<Boolean> decisionByAdmin(@PathVariable("approvalId") Long approvalId,
                                                @RequestBody ApprovalDto.Admin dto,
                                                HttpServletRequest request){

        String authorization = request.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        approvalService.approvalDecisionByAdmin(approvalId, dto);

        return ResponseEntity.ok(true);
    }

    /**
     * Office 내 Approval 모든 리스트 불러오기
     */
    @Schema(name = "Find Approval List")
    @GetMapping(value = "/list/{userId}")
    public ResponseEntity<List> findApprovalList(@PathVariable("userId") Long userId){

        List<ApprovalDto.Response> responseList = approvalService.approvalListForOffice(userId).stream()
                .map(ApprovalDto.Response::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }

    /**
     * 대기중인 Approval List 조회
     */
    @Schema(name = "All Approval List")
    @GetMapping(value = "/list/wait/{userId}")
    public ResponseEntity<List> findWaitApproval(@PathVariable("userId") Long userId){

        List<Approval> list = approvalService.approvalListForOffice(userId).stream()
                .filter(approval -> approval.getDecision().equals(Decision.WAIT))
                .collect(Collectors.toList());

        return ResponseEntity.ok(list);
    }

    /**
     * 승인된 Approval List 조회
     */
    @Schema(name = "All Approval List")
    @GetMapping(value = "/list/confirm/{userId}")
    public ResponseEntity<List> findConfirmApproval(@PathVariable("userId") Long userId){

        List<Approval> list = approvalService.approvalListForOffice(userId).stream()
                .filter(approval -> approval.getDecision().equals(Decision.CONFIRM))
                .collect(Collectors.toList());

        return ResponseEntity.ok(list);
    }

    /**
     * 거절된 Approval List 조회
     */
    @Schema(name = "All Approval List")
    @GetMapping(value = "/list/reject/{userId}")
    public ResponseEntity<List> findAll(@PathVariable("userId") Long userId){

        List<Approval> list = approvalService.approvalListForOffice(userId).stream()
                .filter(approval -> approval.getDecision().equals(Decision.REJECT))
                .collect(Collectors.toList());

        return ResponseEntity.ok(list);
    }
}
