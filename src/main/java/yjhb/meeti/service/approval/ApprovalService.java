package yjhb.meeti.service.approval;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import yjhb.meeti.domain.user.constant.Role;
import yjhb.meeti.dto.approval.ApprovalDto;
import yjhb.meeti.domain.approval.constant.Decision;
import yjhb.meeti.domain.approval.entity.Approval;
import yjhb.meeti.dto.user.UserInfoDto;
import yjhb.meeti.global.error.exception.BusinessException;
import yjhb.meeti.repository.approval.ApprovalRepository;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.global.error.ErrorCode;
import yjhb.meeti.global.error.exception.EntityNotFoundException;
import yjhb.meeti.service.file.S3Service;
import yjhb.meeti.service.user.UserService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApprovalService {

    private final ApprovalRepository approvalRepository;
    private final S3Service s3Service;
    private final UserService userService;

    public Approval findApprovalById(Long id){
        return approvalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_EXISTS));
    }

    public Long regApproval(ApprovalDto.Request dto, User user, MultipartFile file) throws IOException {

        Approval approval = Approval.builder()
                .user(user)
                .adminUsername(dto.getAdminUsername())
                .requestDetail(dto.getRequestDetail())
                .decision(Decision.WAIT)
                .file(s3Service.upload(file, "approvalFile"))
                .build();

        approvalRepository.save(approval);

        return approval.getId();
    }

    public void proceed(Long id, Decision decision){

        Approval findApproval = findApprovalById(id);

        findApproval.updateDecision(decision);
    }

    public void update(Long id, ApprovalDto.Request dto, MultipartFile file) throws IOException {

        Approval approval = approvalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_APPROVAL));

        approval.update(dto.getAdminUsername(), dto.getRequestDetail(), s3Service.upload(file, "approvalFile"));
    }

    // 같은 Office 내 결재 리스트 조회e
    public List<ApprovalDto.Response> approvalListForOffice(Long userId){

        String email = userService.findUserByUserId(userId).getEmail().split("@")[1];

        List<ApprovalDto.Response> findList = approvalRepository.findAll().stream()
                .filter(approval -> approval.getUser().getEmail().split("@")[1].equals(email))
                .map(ApprovalDto.Response::from)
                .collect(Collectors.toList());

        return findList;
    }

    public void approvalDecisionByAdmin(Long approvalId, ApprovalDto.Admin dto){

        Decision decision = null;

        if (dto.getDecision().equals(Decision.CONFIRM.toString()))
            decision = Decision.CONFIRM;
        if (dto.getDecision().equals(Decision.REJECT.toString()))
            decision = Decision.REJECT;
        else decision = Decision.WAIT;

        findApprovalById(approvalId).adminUpdate(dto.getDecisionDetail(), decision);
    }

    public void deleteApproval(Long userId, Long approvalId){

        Approval findApproval = findApprovalById(approvalId);

        if (!findApproval.getUser().getId().equals(userId))
            throw new BusinessException(ErrorCode.NOT_VALID_USER);

        approvalRepository.delete(findApproval);
    }

    public List<UserInfoDto> findAdminByUserEmail(String email) {

        String domain = email.split("@")[1];

        return userService.findAll().stream()
                .filter(user -> user.getEmail().split("@")[1].equals(domain))
                .filter(user -> user.getRole().equals(Role.ADMIN_OFFICE))
                .map(UserInfoDto::from)
                .collect(Collectors.toList());
    }
}
