package yjhb.meeti.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import yjhb.meeti.global.jwt.dto.JwtTokenDto;

import java.util.Date;

public class LoginDTO {

    @Getter @Setter
    public static class Request{
        private String email;
        private String password;
    }

    @Getter @Setter
    @Builder @NoArgsConstructor @AllArgsConstructor
    public static class Response{

        @Schema(description = "로그인 유저의 고유 id", example = "1", required = true)
        private Long id;
        @Schema(description = "인증 방식을 정의하는 타입 (JWT/OAuth 부여 방식 : Bearer)", example = "Bearer", required = true)
        private String grantType;
        @Schema(description = "User Access Token", required = true)
        private String accessToken;
        @Schema(description = "User Access Token 만료 시간", example = "2023-07-23 20:55:00", required = true)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private Date accessTokenExpireTime;
        @Schema(description = "Access Token이 만료된 후, 재발급하기 위한 User Refresh Token", required = true)
        private String refreshToken;
        @Schema(description = "User Refresh Token 만료 시간", example = "2023-07-23 20:55:00", required = true)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private Date refreshTokenExpireTime;

        // of 메서드를 Service에 만들지 않고 숨김
        // Service의 가독성을 위해 사용
        public static Response of(JwtTokenDto jwtTokenDto){
            return Response.builder()
                    .id(jwtTokenDto.getId())
                    .grantType(jwtTokenDto.getGrantType())
                    .accessToken(jwtTokenDto.getAccessToken())
                    .accessTokenExpireTime(jwtTokenDto.getAccessTokenExpireTime())
                    .refreshToken(jwtTokenDto.getRefreshToken())
                    .refreshTokenExpireTime(jwtTokenDto.getRefreshTokenExpireTime())
                    .build();
        }
    }
}
