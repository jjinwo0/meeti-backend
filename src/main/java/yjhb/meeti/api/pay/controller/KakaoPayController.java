package yjhb.meeti.api.pay.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yjhb.meeti.api.pay.dto.KakaoApproveResponseDto;
import yjhb.meeti.api.pay.dto.KakaoReadyResponseDto;
import yjhb.meeti.domain.kakaopay.service.KakaoPayService;
import yjhb.meeti.global.error.ErrorCode;
import yjhb.meeti.global.error.exception.BusinessException;

@RestController
@RequestMapping("/meeti/kakao/payment")
@RequiredArgsConstructor
public class KakaoPayController {

    private final KakaoPayService kakaoPayService;

    // 결제 요청
    @PostMapping("/ready")
    public ResponseEntity<KakaoReadyResponseDto> readyToKakaoPay(){

        return ResponseEntity.ok(kakaoPayService.kakaoPayReady());
    }

    // 결제 취소
    @GetMapping("/cancel")
    public void cancel(){

        throw new BusinessException(ErrorCode.INVALID_PAYMENT);
    }

    // 결제 실패
    @GetMapping("/fail")
    public void fail(){

        throw new BusinessException(ErrorCode.INVALID_PAYMENT);
    }

    /**
     * 결제 성공
     */
    @GetMapping("/success")
    public ResponseEntity afterPayRequest(@RequestParam("token") String token) {

        KakaoApproveResponseDto kakaoApprove = kakaoPayService.approveResponse(token);

        return new ResponseEntity<>(kakaoApprove, HttpStatus.OK);
    }
}
