package yjhb.meeti.dto.payment;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class KakaoReadyResponseDto {

    private Long id;
    private String redirectUrl;
    private String createAt;
}
