package yjhb.meeti.api.pay;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yjhb.meeti.dto.payment.request.KakaoApproveRequestDto;
import yjhb.meeti.dto.payment.request.KakaoCompletedRequestDto;
import yjhb.meeti.dto.payment.response.KakaoApproveResponseDto;
import yjhb.meeti.dto.payment.response.KakaoCancelResponseDto;
import yjhb.meeti.dto.payment.response.KakaoReadyResponseDto;
import yjhb.meeti.service.payment.KakaoPayService;
import yjhb.meeti.global.error.ErrorCode;
import yjhb.meeti.global.error.exception.BusinessException;
import yjhb.meeti.global.jwt.service.TokenManager;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "KakaoPay", description = "카카오페이 결제 API")
@Slf4j
@RestController
@RequestMapping("/kakao/payment")
@RequiredArgsConstructor
public class KakaoPayController {

    private final KakaoPayService kakaoPayService;
    private final TokenManager tokenManager;

    // 결제 요청
    @Tag(name = "Ready to KakaoPay")
    @PostMapping("/ready")
    public ResponseEntity<KakaoReadyResponseDto> readyToKakaoPay(@RequestBody KakaoApproveRequestDto requestDto){

        log.info("approveRequestDto : " + requestDto.getItem_name());
        log.info("approveRequestDto.getPartner_order_id : " + requestDto.getPartner_order_id());

        KakaoReadyResponseDto readyResponseDto = kakaoPayService.kakaoPayReady(requestDto);
        System.out.println("readyResponseDto.getId() : " + readyResponseDto.getId());

        return ResponseEntity.ok(readyResponseDto);
    }

    // 결제 취소
    @Tag(name = "Cancel")
    @GetMapping("/cancel")
    public void cancel(){

        throw new BusinessException(ErrorCode.INVALID_PAYMENT);
    }

    // 결제 실패
    @Tag(name = "Failed")
    @GetMapping("/fail")
    public void fail(){

        throw new BusinessException(ErrorCode.INVALID_PAYMENT);
    }

    /**
     * 결제 완료
     */
    @Tag(name = "Completed")
    @GetMapping("/completed")
    public ResponseEntity afterPayRequest(@RequestBody KakaoCompletedRequestDto completedRequestDto) {

        log.info("completedRequestDto.getCid() : " + completedRequestDto.getCid());

        KakaoApproveResponseDto approveResponseDto = kakaoPayService.approveResponse(completedRequestDto);

        log.info("approveResponseDto.getItem_code() : " + approveResponseDto.getItem_code());

        return new ResponseEntity<>(approveResponseDto, HttpStatus.OK);
    }

    /**
     * 환불
     */
    @Tag(name = "Refund")
    @PostMapping("/refund")
    public ResponseEntity refund() {

        KakaoCancelResponseDto kakaoCancelResponse = kakaoPayService.kakaoCancel();

        return new ResponseEntity<>(kakaoCancelResponse, HttpStatus.OK);
    }
}
