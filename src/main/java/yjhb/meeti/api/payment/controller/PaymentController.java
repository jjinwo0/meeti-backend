package yjhb.meeti.api.payment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yjhb.meeti.api.payment.dto.PaymentDto;
import yjhb.meeti.api.payment.service.PaymentService;
import yjhb.meeti.global.config.TossPaymentConfig;
import yjhb.meeti.global.jwt.service.TokenManager;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/meeti/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final TossPaymentConfig tossPaymentConfig;
    private final TokenManager tokenManager;

    @PostMapping("/toss")
    public ResponseEntity<?> requestTossPayment(@RequestBody PaymentDto.Request paymentRequestDto,
                                                HttpServletRequest httpServletRequest){

        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);
    }

}
