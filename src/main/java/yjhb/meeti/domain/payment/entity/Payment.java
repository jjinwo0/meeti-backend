package yjhb.meeti.domain.payment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yjhb.meeti.api.payment.dto.PaymentDto;
import yjhb.meeti.domain.common.BaseTimeEntity;
import yjhb.meeti.domain.payment.constant.PayType;
import yjhb.meeti.domain.reservation.entity.Reservation;
import yjhb.meeti.domain.user.entity.User;

import javax.persistence.*;

@Entity
@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Payment extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private PayType payType;
    private Long amount;
    private String orderName;
    private String orderId;
    private boolean paySuccessYN;
    @OneToOne(mappedBy = "reservation_id")
    private Reservation reservation;
    private String paymentKey;
    private String failReason;
    private boolean cancelYN;
    private String cancelReason;

    public PaymentDto.Response toPaymentResponseDto(){

        return PaymentDto.Response.builder()
                .payType(payType.getDescription())
                .amount(amount)
                .orderName(orderName)
                .orderId(orderId)
                .userEmail(reservation.getUser().getEmail())
                .username(reservation.getUser().getUsername())
                .createdAt(toPaymentResponseDto().getCreatedAt())
                .cancelYN(cancelYN)
                .failReason(failReason)
                .build();
    }
}
