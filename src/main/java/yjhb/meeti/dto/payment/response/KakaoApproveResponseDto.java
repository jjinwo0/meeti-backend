package yjhb.meeti.dto.payment.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class KakaoApproveResponseDto {

    private String aid;
    private String tid;
    private String cid;
    private String sid;
    private String partner_order_id; // 가맹점 주문 번호
    private String partner_user_id; // 가맹점 회원 id
    private String payment_method_type; // 결제 수단
    private Amount amount; // 결제 금액 정보
    private String item_name; // 상품명
    private String item_code; // 상품 코드
    private int quantity; // 상품 수량
    private String created_at; // 결제 요청 시간
    private String approved_at; // 결제 승인 시간
    private String payload; // 결제 승인 요청에 대해 저장 값, 요청 시 전달 내용

    @Data
    public static class Amount{


        private int total; // 총 결제 금액
        private int tax_free; // 비과세 금액
        private int tax; // 부가세 금액
        private int point;
        private int discount;
    }
}
