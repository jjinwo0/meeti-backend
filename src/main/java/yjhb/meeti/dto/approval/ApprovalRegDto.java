package yjhb.meeti.dto.approval;

import lombok.*;

import java.io.File;

@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class ApprovalRegDto {

    private String requestDetail;
    private String proceeding;
    private String decisionDetail;
    private String decision;
}
