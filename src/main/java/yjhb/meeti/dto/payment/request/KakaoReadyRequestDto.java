package yjhb.meeti.dto.payment.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class KakaoReadyRequestDto {

    private String tid;
    private String redirect_url;
    private String partner_order_id;
}
