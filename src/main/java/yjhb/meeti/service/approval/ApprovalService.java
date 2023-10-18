package yjhb.meeti.service.approval;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import yjhb.meeti.dto.approval.ApprovalDto;
import yjhb.meeti.domain.approval.constant.Decision;
import yjhb.meeti.domain.approval.entity.Approval;
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
                .requestDetail(dto.getRequestDetail())
                .proceeding(dto.getProceeding())
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

        approval.update(dto.getRequestDetail(), dto.getProceeding(), s3Service.upload(file, "approvalFile"));
    }

    // 같은 Office 내 결재 리스트 조회
    public List<Approval> approvalListForOffice(Long userId){

        String email = userService.findUserByUserId(userId).getEmail();

        List<Approval> findList = approvalRepository.findAll().stream()
                .filter(approval -> approval.getUser().getEmail().split("@")[1].equals(email))
                .collect(Collectors.toList());

        return findList;
    }

    public void approvalDecisionByAdmin(Long approvalId, ApprovalDto.Admin dto){

        findApprovalById(approvalId).adminUpdate(dto.getDecisionDetail(), dto.getDecision());
    }
}
