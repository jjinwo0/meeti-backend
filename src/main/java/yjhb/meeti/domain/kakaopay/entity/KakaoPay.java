package yjhb.meeti.domain.kakaopay.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yjhb.meeti.domain.common.BaseTimeEntity;
import yjhb.meeti.domain.reservation.entity.Reservation;

import javax.persistence.*;

@Entity
@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class KakaoPay extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
}
