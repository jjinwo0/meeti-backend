package yjhb.meeti.dto.reservation;

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
    @Schema(description = "대여 시작 시간")
    private String startTime;
    @Schema(description = "대여 종료 시간")
    private String endTime;
    @Schema(description = "예약할 오피스의 id")
    private Long officeId;
}
