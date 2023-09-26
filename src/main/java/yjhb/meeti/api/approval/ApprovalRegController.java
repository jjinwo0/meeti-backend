package yjhb.meeti.api.approval;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yjhb.meeti.dto.approval.ApprovalRegDto;
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
public class ApprovalRegController {

    private final TokenManager tokenManager;
    private final ApprovalService approvalService;
    private final UserService userService;

    @Tag(name = "Approval Registration")
    @PostMapping(value = "/approval/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Boolean> registrationApproval(@PathVariable("userId") Long userId,
                                                        @RequestBody ApprovalRegDto dto,
                                                        @RequestPart(value = "file") MultipartFile file,
                                                        HttpServletRequest httpServletRequest) throws IOException {

        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        User findUser = userService.findUserByUserId(userId);

        approvalService.regApproval(dto, findUser, file);

        return ResponseEntity.ok(true);
    }
}
