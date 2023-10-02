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

    @Value("${kakao.client.id}")
    private String cid;
    @Value("${kakao.admin.key}")
    private String adminKey;


    public KakaoReadyResponseDto kakaoPayReady(KakaoApproveRequestDto dto) {

        // 카카오페이 요청 양식
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("partner_order_id", dto.getPartner_order_id());
        parameters.add("partner_user_id", dto.getPartner_user_id());
        parameters.add("item_name", dto.getItem_name());
        parameters.add("item_code", dto.getItem_code());
        parameters.add("quantity", String.valueOf(dto.getQuantity()));
        parameters.add("total_amount", String.valueOf(dto.getTotal_amount()));
        parameters.add("tax_free_amount", String.valueOf(dto.getTax_free_amount()));
        parameters.add("approval_url", "http://localhost:8080/meeti/kakao/payment/completed"); // 성공 시 redirect url
        parameters.add("cancel_url", "http://localhost:8080/meeti/kakao/payment/cancel"); // 취소 시 redirect url
        parameters.add("fail_url", "http://localhost:8080/meeti/kakao/payment/fail"); // 실패 시 redirect url

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        KakaoReadyResponseDto readyResponseDto = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/ready",
                requestEntity,
                KakaoReadyResponseDto.class);

        readyResponseDto.setPartner_order_id(dto.getPartner_order_id());

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
    public KakaoApproveResponseDto approveResponse(KakaoCompletedRequestDto dto){

        // 카카오 요청
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

        parameters.add("cid", cid);
        parameters.add("tid", dto.getTid());
        parameters.add("partner_order_id", dto.getPartner_order_id());
        parameters.add("partner_user_id", dto.getPartner_user_id());
        parameters.add("pg_token", dto.getPg_token());

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        KakaoApproveResponseDto approveResponse = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/approve",
                requestEntity,
                KakaoApproveResponseDto.class);

        return approveResponse;
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

        KakaoCancelResponseDto cancelResponse = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/cancel",
                requestEntity,
                KakaoCancelResponseDto.class);

        return cancelResponse;
    }
}
