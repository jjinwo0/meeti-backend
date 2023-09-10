package yjhb.meeti.api.reservation.registration.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class ReservationRegDto {

    @Schema(description = "예약 날짜 설정")
    private String date;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
//    private LocalDate date;
    @Schema(description = "대여 시작 시간")
    private String startTime;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "Asia/Seoul")
//    private LocalTime startTime;
    @Schema(description = "대여 종료 시간")
    private String endTime;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "Asia/Seoul")
//    private LocalTime endTime;
    @Schema(description = "예약할 오피스의 id")
    private Long officeId;
}
