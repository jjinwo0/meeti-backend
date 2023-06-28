package yjhb.meeti.global.error;

import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class FeingClientExceptionErrorDecoder implements ErrorDecoder{

    private ErrorDecoder errorDecoder = new ErrorDecoder.Default(); // 기본 Error Decoder

    @Override
    public Exception decode(String methodKey, Response response) {
        log.error("{} 요청 실패, status : {}, reason : {}", methodKey, response.status(), response.reason());

        FeignException exception = FeignException.errorStatus(methodKey, response);
        HttpStatus httpStatus = HttpStatus.valueOf(response.status());

        if (httpStatus.is5xxServerError()){ // 500에러 발생 시, 재시도
            return new RetryableException(
                    response.status(), // int status
                    exception.getMessage(), // String message
                    response.request().httpMethod(), // HttpMethod httpMethod
                    exception,
                    null,
                    response.request()
            );
        }
        return errorDecoder.decode(methodKey, response);
    }
}
