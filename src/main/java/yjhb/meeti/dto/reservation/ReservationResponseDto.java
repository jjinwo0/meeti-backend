package yjhb.meeti.dto.reservation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yjhb.meeti.domain.reservation.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class ReservationResponseDto {

    private Long id;
    @Schema(description = "예약 날짜 설정")
    private LocalDate date;
    @Schema(description = "대여 시작 시간")
    private LocalTime startTime;
    @Schema(description = "대여 종료 시간")
    private LocalTime endTime;
    @Schema(description = "예약 오피스의 이름")
    private String officeName;
    private String telNum;
    private String image;

    public static ReservationResponseDto of(Reservation reservation){

        return ReservationResponseDto.builder()
                .id(reservation.getId())
                .date(reservation.getDate())
                .startTime(reservation.getStartTime())
                .endTime(reservation.getEndTime())
                .officeName(reservation.getOffice().getPlaceName())
                .telNum(reservation.getOffice().getTelNum())
                .image(reservation.getOffice().getImage())
                .build();
    }
}
