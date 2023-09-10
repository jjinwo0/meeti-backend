package yjhb.meeti.api.reservation.search.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class ReservationResponseDto {

    @Schema(description = "예약 날짜 설정")
    private LocalDate date;
    @Schema(description = "대여 시작 시간")
    private LocalTime startTime;
    @Schema(description = "대여 종료 시간")
    private LocalTime endTime;
    @Schema(description = "예약 오피스의 이름")
    private String officeName;
}
