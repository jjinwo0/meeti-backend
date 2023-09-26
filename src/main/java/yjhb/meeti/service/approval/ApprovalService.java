package yjhb.meeti.service.approval;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import yjhb.meeti.dto.approval.ApprovalRegDto;
import yjhb.meeti.domain.approval.constant.Decision;
import yjhb.meeti.domain.approval.entity.Approval;
import yjhb.meeti.repository.approval.ApprovalRepository;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.global.error.ErrorCode;
import yjhb.meeti.global.error.exception.EntityNotFoundException;
import yjhb.meeti.service.file.S3Service;

import java.io.IOException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApprovalService {

    private final ApprovalRepository approvalRepository;
    private final S3Service s3Service;

    public Approval findApprovalById(Long id){
        return approvalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_EXISTS));
    }

    public Long regApproval(ApprovalRegDto dto, User user, MultipartFile file) throws IOException {

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

    public void update(Long id, ApprovalRegDto dto, MultipartFile file) throws IOException {

        Approval approval = approvalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND_APPROVAL));

        approval.update(dto.getRequestDetail(), dto.getProceeding(), dto.getDecisionDetail(), dto.getDecision(), s3Service.upload(file, "approvalFile"));
    }
}
