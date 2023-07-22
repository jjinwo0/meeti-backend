package yjhb.meeti.api.registration.approval.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.api.registration.approval.dto.ApprovalRegDto;
import yjhb.meeti.domain.approval.entity.Approval;
import yjhb.meeti.domain.approval.repository.ApprovalRepository;
import yjhb.meeti.domain.user.entity.User;

@Service
@Transactional
@RequiredArgsConstructor
public class ApprovalRegService {

    private final ApprovalRepository approvalRepository;

    public Long regApproval(ApprovalRegDto dto, User user){
        Approval approval = Approval.builder()
                .username(user.getUsername())
                .requestDetail(dto.getRequestDetail())
                .file(dto.getFile())
                .decisionDetail(dto.getDecisionDetail())
                .build();

        approvalRepository.save(approval);

        return approval.getId();
    }
}
