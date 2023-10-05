package yjhb.meeti.dto.approval;

import lombok.*;
import yjhb.meeti.domain.approval.constant.Decision;

import java.io.File;

@Getter
@NoArgsConstructor
public class ApprovalRegDto {

    private String requestDetail;
    private String proceeding;
    private String decisionDetail;
    private Decision decision;

    @Builder
    public ApprovalRegDto(String requestDetail, String proceeding, String decisionDetail, Decision decision) {
        this.requestDetail = requestDetail;
        this.proceeding = proceeding;
        this.decisionDetail = decisionDetail;
        this.decision = decision;
    }
}
