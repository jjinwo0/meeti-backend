package yjhb.meeti.api.approval.registration.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.api.approval.registration.dto.ApprovalRegDto;
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
                .user(user)
                .requestDetail(dto.getRequestDetail())
                .proceeding(dto.getProceeding())
                .build();

        approvalRepository.save(approval);

        return approval.getId();
    }
}
