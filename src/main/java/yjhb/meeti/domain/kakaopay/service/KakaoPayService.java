package yjhb.meeti.domain.kakaopay.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import yjhb.meeti.api.pay.dto.KakaoApproveResponseDto;
import yjhb.meeti.api.pay.dto.KakaoReadyResponseDto;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KakaoPayService {

    @Value("${kakao.client.id}")
    private final String cid;
    @Value("${kakao.admin.key}")
    private final String adminKey;
    private KakaoReadyResponseDto kakaoPayResponseDto;


    public KakaoReadyResponseDto kakaoPayReady() {

        // 카카오페이 요청 양식
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("partner_order_id", "가맹점 주문 번호");
        parameters.add("partner_user_id", "가맹점 회원 ID");
        parameters.add("item_name", "상품명");
        parameters.add("quantity", "주문 수량");
        parameters.add("total_amount", "총 금액");
        parameters.add("vat_amount", "부가세");
        parameters.add("tax_free_amount", "상품 비과세 금액");
        parameters.add("approval_url", "http://localhost:8080/meeti/kakao/payment/success"); // 성공 시 redirect url
        parameters.add("cancel_url", "http://localhost:8080/meeti/kakao/payment/cancel"); // 취소 시 redirect url
        parameters.add("fail_url", "http://localhost:8080/meeti/kakao/payment/fail"); // 실패 시 redirect url

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        kakaoPayResponseDto = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/ready",
                requestEntity,
                KakaoReadyResponseDto.class);

        return kakaoPayResponseDto;
    }

    /**
     * 카카오 요구 헤더값
     */
    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();

        String auth = "KakaoAK " + adminKey;

        httpHeaders.set("Authorization", auth);
        httpHeaders.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return httpHeaders;
    }

    /**
     * 결제 완료 승인
     */
    public KakaoApproveResponseDto approveResponse(String token){



        // 카카오 요청
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("id", String.valueOf(kakaoPayReady().getId()));
        parameters.add("partner_order_id", "가맹점 주문 번호");
        parameters.add("partner_user_id", "가맹점 회원 ID");
        parameters.add("pg_token", token);

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        KakaoApproveResponseDto approveResponse = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/approve",
                requestEntity,
                KakaoApproveResponseDto.class);

        return approveResponse;
    }
}
