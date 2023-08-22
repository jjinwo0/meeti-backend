package yjhb.meeti.global.config;

import org.springframework.beans.factory.annotation.Value;

public class TossPaymentConfig {

    @Value("${tosspayments.apiKey}")
    private String apiKey;
    @Value("${tosspayments.secret}")
    private String secretKey;
}
