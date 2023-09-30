package yjhb.meeti.dto.payment.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class KakaoReadyResponseDto {

    private Long id;
    private String redirect_url;
    private String partner_order_id;
}
