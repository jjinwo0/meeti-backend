package yjhb.meeti.service.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import yjhb.meeti.dto.payment.request.KakaoApproveRequestDto;
import yjhb.meeti.dto.payment.request.KakaoCompletedRequestDto;
import yjhb.meeti.dto.payment.response.KakaoApproveResponseDto;
import yjhb.meeti.dto.payment.response.KakaoCancelResponseDto;
import yjhb.meeti.dto.payment.response.KakaoReadyResponseDto;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KakaoPayService {

//    @Value("${kakao.client.id}")
    private static String cid = "TC0ONETIME";
    @Value(value = "${kakao.admin.key}")
    private String adminKey;
    private static String partner_order_id = "0320";
    private static String partner_user_id = "mintmin";
    private static int quantity = 1;
    private static int tax_free_amount = 0;

    private KakaoReadyResponseDto readyResponseDto;


    public KakaoReadyResponseDto kakaoPayReady(KakaoApproveRequestDto dto) {

        // 카카오페이 요청 양식
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("partner_order_id", partner_order_id);
        parameters.add("partner_user_id", partner_user_id);
        parameters.add("item_name", dto.getItem_name());
        parameters.add("item_code", dto.getItem_code());
        parameters.add("quantity", String.valueOf(quantity));
        parameters.add("total_amount", String.valueOf(dto.getTotal_amount()));
        parameters.add("tax_free_amount", String.valueOf(tax_free_amount));
        parameters.add("approval_url", "http://localhost:8080/kakao/payment/completed"); // 성공 시 redirect url
        parameters.add("cancel_url", "http://localhost:8080/kakao/payment/cancel"); // 취소 시 redirect url
        parameters.add("fail_url", "http://localhost:8080/kakao/payment/fail"); // 실패 시 redirect url

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        readyResponseDto = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/ready",
                requestEntity,
                KakaoReadyResponseDto.class);

        return readyResponseDto;
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
    public KakaoApproveResponseDto approveResponse(String pg_token){

        // 카카오 요청
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

        parameters.add("cid", cid);
        parameters.add("tid", readyResponseDto.getTid());
        parameters.add("partner_order_id", partner_order_id);
        parameters.add("partner_user_id", partner_user_id);
        parameters.add("pg_token", pg_token);

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        KakaoApproveResponseDto kakaoApproveResponseDto = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/approve",
                requestEntity,
                KakaoApproveResponseDto.class);

        return kakaoApproveResponseDto;
    }


    /**
     * 결제 환불
     */
    public KakaoCancelResponseDto kakaoCancel() {

        // 카카오페이 요청
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("tid", "환불할 결제 고유 번호");
        parameters.add("cancel_amount", "환불 금액");
        parameters.add("cancel_tax_free_amount", "환불 비과세 금액");
        parameters.add("cancel_vat_amount", "환불 부가세");

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/cancel",
                requestEntity,
                KakaoCancelResponseDto.class);
    }
}
