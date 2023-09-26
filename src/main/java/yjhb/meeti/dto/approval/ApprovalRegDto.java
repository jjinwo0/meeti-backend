package yjhb.meeti.dto.approval;

import lombok.*;
import yjhb.meeti.domain.approval.constant.Decision;

import java.io.File;

@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class ApprovalRegDto {

    private String requestDetail;
    private String proceeding;
    private String decisionDetail;
    private Decision decision;
}
