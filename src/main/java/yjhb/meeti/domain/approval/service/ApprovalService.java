package yjhb.meeti.domain.approval.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.api.approval.registration.dto.ApprovalRegDto;
import yjhb.meeti.domain.approval.constant.Decision;
import yjhb.meeti.domain.approval.entity.Approval;
import yjhb.meeti.domain.approval.repository.ApprovalRepository;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.global.error.ErrorCode;
import yjhb.meeti.global.error.exception.EntityNotFoundException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApprovalService {

    private final ApprovalRepository approvalRepository;

    public Approval findApprovalById(Long id){
        return approvalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_EXISTS));
    }

    public Long regApproval(ApprovalRegDto dto, User user){
        Approval approval = Approval.builder()
                .user(user)
                .requestDetail(dto.getRequestDetail())
                .proceeding(dto.getProceeding())
                .build();

        approvalRepository.save(approval);

        return approval.getId();
    }

    public void proceed(Long id, Decision decision){

        Approval findApproval = findApprovalById(id);

        findApproval.updateDecision(decision);
    }
}
