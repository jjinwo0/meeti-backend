package yjhb.meeti.api.pay.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class KakaoReadyResponseDto {

    private Long id;
    private String redirectUrl;
    private String createAt;
}
