package yjhb.meeti.api.approval.registration.dto;

import lombok.*;

import java.io.File;

@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class ApprovalRegDto {

    private String username;
    private String requestDetail;
    private File file;
    private String decisionDetail;
    private String decision;
}
