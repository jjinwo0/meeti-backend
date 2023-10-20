package yjhb.meeti.dto.approval;

import lombok.*;
import yjhb.meeti.domain.approval.constant.Decision;
import yjhb.meeti.domain.approval.entity.Approval;

@Getter
@NoArgsConstructor
public class ApprovalDto {

    @Data
    @NoArgsConstructor
    public static class Request{

        private String adminUsername;
        private String requestDetail;

        @Builder
        public Request(String adminUsername, String requestDetail) {
            this.adminUsername = adminUsername;
            this.requestDetail = requestDetail;
        }

    }

    @Data
    @NoArgsConstructor
    public static class Admin{

        private String decisionDetail;
        private String decision;

        @Builder
        public Admin(String decisionDetail, String decision) {
            this.decisionDetail = decisionDetail;
            this.decision = decision;
        }
    }

    @Data
    @NoArgsConstructor
    public static class Response{

        private Long id;
        private String requestUsername;
        private String requestDetail;
        private String requestFile;
        private String adminUsername;
        private String decisionDetail;
        private Decision decision;

        @Builder
        public Response(Long id, String requestUsername, String requestDetail, String requestFile, String adminUsername, String decisionDetail, Decision decision) {
            this.id = id;
            this.requestUsername = requestUsername;
            this.requestDetail = requestDetail;
            this.requestFile = requestFile;
            this.adminUsername = adminUsername;
            this.decisionDetail = decisionDetail;
            this.decision = decision;
        }

        public static Response from(Approval approval){

            return Response.builder()
                    .id(approval.getId())
                    .requestUsername(approval.getUser().getUsername())
                    .requestDetail(approval.getRequestDetail())
                    .requestFile(approval.getFile())
                    .adminUsername(approval.getAdminUsername())
                    .decisionDetail(approval.getDecisionDetail())
                    .decision(approval.getDecision())
                    .requestFile(approval.getFile())
                    .build();
        }
    }
}
