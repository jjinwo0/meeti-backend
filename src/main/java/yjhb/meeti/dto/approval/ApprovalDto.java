package yjhb.meeti.dto.approval;

import lombok.*;
import yjhb.meeti.domain.approval.constant.Decision;
import yjhb.meeti.domain.approval.entity.Approval;

@Getter
@NoArgsConstructor
public class ApprovalDto {

    @Data
    public static class Request{

        private String requestDetail;
        private String proceeding;

        @Builder
        public Request(String requestDetail, String proceeding) {
            this.requestDetail = requestDetail;
            this.proceeding = proceeding;
        }

    }

    @Data
    public static class Admin{

        private String decisionDetail;
        private Decision decision;

        @Builder
        public Admin(String decisionDetail, Decision decision) {
            this.decisionDetail = decisionDetail;
            this.decision = decision;
        }
    }

    @Data
    public static class Response{

        private String requestDetail;
        private String proceeding;
        private String decisionDetail;
        private Decision decision;

        @Builder
        public Response(String requestDetail, String proceeding, String decisionDetail, Decision decision) {
            this.requestDetail = requestDetail;
            this.proceeding = proceeding;
            this.decisionDetail = decisionDetail;
            this.decision = decision;
        }

        public static Response from(Approval approval){

            return Response.builder()
                    .requestDetail(approval.getRequestDetail())
                    .proceeding(approval.getProceeding())
                    .decisionDetail(approval.getDecisionDetail())
                    .decision(approval.getDecision())
                    .build();
        }
    }
}
