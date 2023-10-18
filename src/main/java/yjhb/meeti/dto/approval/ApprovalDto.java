package yjhb.meeti.dto.approval;

import lombok.*;
import yjhb.meeti.domain.approval.constant.Decision;

@Getter
@NoArgsConstructor
public class ApprovalDto {

    @Data
    public static class Request{

        private String requestDetail;
        private String proceeding;
        private String decisionDetail;
        private Decision decision;

        @Builder
        public Request(String requestDetail, String proceeding, String decisionDetail, Decision decision) {
            this.requestDetail = requestDetail;
            this.proceeding = proceeding;
            this.decisionDetail = decisionDetail;
            this.decision = decision;
        }

    }
}
