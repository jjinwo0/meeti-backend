package yjhb.meeti.api.approval.process.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.domain.approval.constant.Decision;
import yjhb.meeti.domain.approval.entity.Approval;
import yjhb.meeti.domain.approval.service.ApprovalService;

@Service
@Transactional
@RequiredArgsConstructor
public class ApprovalProcessService {
    private final ApprovalService approvalService;

    public void proceed(Long id, Decision decision){

        Approval findApproval = approvalService.findApprovalById(id);

        findApproval.updateDecision(decision);
    }
}
