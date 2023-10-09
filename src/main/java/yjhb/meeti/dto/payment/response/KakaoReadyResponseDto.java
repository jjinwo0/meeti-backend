package yjhb.meeti.dto.payment.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class KakaoReadyResponseDto {

    private Long tid;
    private String next_redirect_pc_url;
    private String created_at;
}
