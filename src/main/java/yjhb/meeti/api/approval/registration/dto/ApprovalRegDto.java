package yjhb.meeti.api.approval.registration.dto;

import lombok.*;

import java.io.File;

@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class ApprovalRegDto {

    private String username;
    private String requestDetail;
    private String proceeding;
    private String decisionDetail;
    private String decision;
}
