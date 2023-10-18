package yjhb.meeti.api.approval;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yjhb.meeti.domain.approval.constant.Decision;
import yjhb.meeti.dto.approval.ApprovalDto;
import yjhb.meeti.service.approval.ApprovalService;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.service.user.UserService;
import yjhb.meeti.global.jwt.service.TokenManager;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Tag(name = "Approval Registration", description = "결재 등록 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/meeti/reg")
public class ApprovalController {

    private final TokenManager tokenManager;
    private final ApprovalService approvalService;
    private final UserService userService;

    @Schema(name = "Approval Registration")
    @PostMapping(value = "/approval/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Boolean> registrationApproval(@PathVariable("userId") Long userId,
                                                        @RequestPart(value = "request")String request,
                                                        @RequestPart(value = "proceeding")String proceeding,
                                                        @RequestPart(value = "decision")Decision decision,
                                                        @RequestPart(value = "decisionDetail")String decisionDetail,
                                                        @RequestPart(value = "file") MultipartFile file,
                                                        HttpServletRequest httpServletRequest) throws IOException {

        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        User findUser = userService.findUserByUserId(userId);

        ApprovalDto.Request dto = ApprovalDto.Request.builder()
                .requestDetail(request)
                .proceeding(proceeding)
                .decision(decision)
                .decisionDetail(decisionDetail)
                .build();

        approvalService.regApproval(dto, findUser, file);

        return ResponseEntity.ok(true);
    }

    @Schema(name = "Approval Update")
    @PostMapping(value = "/approval/update/{approvalId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Boolean> updateApproval(@PathVariable("approvalId") Long approvalId,
                                                  @RequestPart(value = "request")String request,
                                                  @RequestPart(value = "proceeding")String proceeding,
                                                  @RequestPart(value = "decision")Decision decision,
                                                  @RequestPart(value = "decisionDetail")String decisionDetail,
                                                  @RequestPart(value = "file") MultipartFile file,
                                                  HttpServletRequest httpServletRequest) throws IOException{

        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        ApprovalDto dto = ApprovalDto.builder()
                .requestDetail(request)
                .proceeding(proceeding)
                .decision(decision)
                .decisionDetail(decisionDetail)
                .build();

        approvalService.update(approvalId, dto, file);

        return ResponseEntity.ok(true);
    }

}
