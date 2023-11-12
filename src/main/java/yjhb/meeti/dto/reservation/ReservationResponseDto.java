package yjhb.meeti.dto.reservation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yjhb.meeti.domain.reservation.Reservation;
import yjhb.meeti.domain.reservation.Status;

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
    private String place;
    private String telNum;
    private String image;

    // todo 추후 수정 예정
    public static ReservationResponseDto of(Reservation reservation){

//        Status status = reservation.getStatus();
        ReservationResponseDto
                dto = ReservationResponseDto.builder()
                .id(reservation.getId())
                .date(reservation.getDate())
                .startTime(reservation.getStartTime())
                .endTime(reservation.getEndTime())
                .officeName(reservation.getOffice().getPlaceName())
                .place(reservation.getOffice().getPlaceName())
                .telNum(reservation.getOffice().getTelNum())
                .image(reservation.getOffice().getImage())
                .build();

        /*
        if(status == Status.WAIT) {
            dto = ReservationResponseDto.builder()
                    .id(reservation.getId())
                    .date(reservation.getDate())
                    .startTime(reservation.getStartTime())
                    .endTime(reservation.getEndTime())
                    .officeName("[대기중] " + reservation.getOffice().getPlaceName())
                    .place(reservation.getOffice().getPlaceName())
                    .telNum(reservation.getOffice().getTelNum())
                    .image(reservation.getOffice().getImage())
                    .build();
        } else if (status == Status.CONFIRM){
            dto = ReservationResponseDto.builder()
                    .id(reservation.getId())
                    .date(reservation.getDate())
                    .startTime(reservation.getStartTime())
                    .endTime(reservation.getEndTime())
                    .officeName(reservation.getOffice().getPlaceName())
                    .place(reservation.getOffice().getPlaceName())
                    .telNum(reservation.getOffice().getTelNum())
                    .image(reservation.getOffice().getImage())
                    .build();
        }else {
            dto = ReservationResponseDto.builder()
                    .id(reservation.getId())
                    .date(reservation.getDate())
                    .startTime(reservation.getStartTime())
                    .endTime(reservation.getEndTime())
                    .officeName("[거절] " + reservation.getOffice().getPlaceName())
                    .place(reservation.getOffice().getPlaceName())
                    .telNum(reservation.getOffice().getTelNum())
                    .image(reservation.getOffice().getImage())
                    .build();
        }
        */

        return dto;
    }
}
