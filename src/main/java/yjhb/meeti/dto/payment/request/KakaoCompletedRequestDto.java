package yjhb.meeti.dto.payment.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class KakaoCompletedRequestDto {

    private String cid;
    private String tid;
    private String partner_order_id;
    private String partner_user_id;
    private String pg_token;
}
