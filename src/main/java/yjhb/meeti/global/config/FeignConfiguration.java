package yjhb.meeti.global.config;

import feign.Logger;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import yjhb.meeti.global.error.FeingClientExceptionErrorDecoder;

@Configuration
@EnableFeignClients(basePackages = "yjhb.meeti") // todo 패키지명 수정
@Import(FeignClientsConfiguration.class)
public class FeignConfiguration {

    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
        // NONE, BASIC, HEADERS
    }

    @Bean
    public ErrorDecoder errorDecoder(){
        return new FeingClientExceptionErrorDecoder();
    }

    @Bean
    public Retryer retryer(){
        // period : 실행 주기
        // maxAttempts : 최대 재시도 횟수
        return new Retryer.Default(1000, 2000, 3);
    }
}
