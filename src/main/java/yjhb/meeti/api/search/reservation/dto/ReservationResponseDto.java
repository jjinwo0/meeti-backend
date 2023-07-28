package yjhb.meeti.api.search.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class ReservationResponseDto {

    @Schema(description = "예약 날짜 설정")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime date;
    @Schema(description = "대여 시작 시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime startTime;
    @Schema(description = "대여 종료 시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime endTime;
    @Schema(description = "예약 오피스의 이름")
    private String officeName;
}
