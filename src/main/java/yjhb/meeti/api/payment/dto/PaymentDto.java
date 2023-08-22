package yjhb.meeti.api.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import yjhb.meeti.domain.payment.constant.PayType;
import yjhb.meeti.domain.payment.entity.Payment;

import java.util.UUID;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class PaymentDto {

    @Data @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class Request{

        private PayType payType;
        private Long amount;
        private String orderName;

        public Payment toEntity(){
            return Payment.builder()
                    .payType(payType)
                    .amount(amount)
                    .orderName(orderName)
                    .orderId(UUID.randomUUID().toString())
                    .paySuccessYN(false)
                    .build();
        }
    }
    @Data @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class Response{

        private String payType;
        private Long amount;
        private String orderName;
        private String orderId;
        private String userEmail;
        private String username;
        private String failReason;
        private boolean cancelYN;
        private String cancelReason;
        private String createdAt;

    }

}
