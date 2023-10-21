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
import yjhb.meeti.dto.user.UserInfoDto;
import yjhb.meeti.service.approval.ApprovalService;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.service.user.UserService;
import yjhb.meeti.global.jwt.service.TokenManager;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Tag(name = "Approval Registration", description = "결재 등록 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/meeti/reg")
public class ApprovalController {

    private final TokenManager tokenManager;
    private final ApprovalService approvalService;
    private final UserService userService;

    @Schema(name = "Find Admin List")
    @GetMapping(value = "/approval/adminList/{userId}")
    public ResponseEntity<List> updateApproval(@PathVariable("userId") Long userId,
                                               HttpServletRequest httpServletRequest){

        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        User findUser = userService.findUserByUserId(userId);

        List<UserInfoDto> findList = approvalService.findAdminByUserEmail(findUser.getEmail());

        return ResponseEntity.ok(findList);
    }

    @Schema(name = "Approval Update")
    @PostMapping(value = "/approval/update/{approvalId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Boolean> updateApproval(@PathVariable("approvalId") Long approvalId,
                                                  @RequestPart(value = "request")String request,
                                                  @RequestPart(value = "adminUsername")String adminUsername,
                                                  @RequestPart(value = "file") MultipartFile file,
                                                  HttpServletRequest httpServletRequest) throws IOException{

        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        ApprovalDto.Request dto = ApprovalDto.Request.builder()
                .adminUsername(adminUsername)
                .requestDetail(request)
                .build();

        approvalService.update(approvalId, dto, file);

        return ResponseEntity.ok(true);
    }

//    @Schema(name = "Approval Registration")
//    @PostMapping(value = "/approval/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<Boolean> registrationApproval(@PathVariable("userId") Long userId,
//                                                        @RequestPart(value = "request")String request,
//                                                        @RequestPart(value = "adminUsername")String adminUsername,
//                                                        @RequestPart(value = "file") MultipartFile file,
//                                                        HttpServletRequest httpServletRequest) throws IOException {
//
//        String authorization = httpServletRequest.getHeader("Authorization");
//        String accessToken = authorization.split(" ")[1];
//
//        tokenManager.validateToken(accessToken);
//
//        User findUser = userService.findUserByUserId(userId);
//
//        ApprovalDto.Request dto = ApprovalDto.Request.builder()
//                .adminUsername(adminUsername)
//                .requestDetail(request)
//                .build();
//
//        approvalService.regApproval(dto, findUser, file);
//
//        return ResponseEntity.ok(true);
//    }

    @Schema(name = "Approval Registration")
    @PostMapping(value = "/approval/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Boolean> registrationApprovalByReservation(@PathVariable("userId") Long userId,
                                                                     @RequestPart(value = "request")String request,
                                                                     @RequestPart(value = "adminUsername")String adminUsername,
                                                                     @RequestPart(value = "placeName")String placeName,
                                                                     @RequestPart(value = "file") MultipartFile file,
                                                                     HttpServletRequest httpServletRequest) throws IOException {

        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        User findUser = userService.findUserByUserId(userId);

        ApprovalDto.ReservationRequest dto = ApprovalDto.ReservationRequest.builder()
                .adminUsername(adminUsername)
                .requestDetail(request)
                .placeName(placeName)
                .build();

        approvalService.regReservationApproval(dto, findUser, file);

        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/approval/delete/{approvalId}/{userId}")
    public ResponseEntity<Boolean> deleteApproval(@PathVariable("userId") Long userId,
                                                  @PathVariable("approvalId") Long approvalId,
                                                  HttpServletRequest httpServletRequest){

        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        approvalService.deleteApproval(userId, approvalId);

        return ResponseEntity.ok(true);
    }
}
