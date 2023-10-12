package yjhb.meeti.dto.calender;

import lombok.*;
import yjhb.meeti.domain.calender.Calendar;

@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class CalenderResponseDto {

    private Long id;
    private String title;
    private String color;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
//    private LocalDate startDate;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "Asia/Seoul")
//    private LocalTime startTime;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
//    private LocalDate endDate;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "Asia/Seoul")
//    private LocalTime endTime;
    private String start;
    private String initTime;
    private String end;
    private String finishTime;
    private String place;

    public static CalenderResponseDto from(Calendar cal){

        return CalenderResponseDto.builder()
                .id(cal.getId())
                .title(cal.getTitle())
                .color(cal.getColor())
                .start(cal.getStart())
                .initTime(cal.getInitTime())
                .end(cal.getEnd())
                .finishTime(cal.getFinishTime())
                .place(cal.getPlace())
                .build();
    }
}
