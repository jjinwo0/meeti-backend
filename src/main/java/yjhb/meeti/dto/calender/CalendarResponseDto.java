package yjhb.meeti.dto.calender;

import lombok.*;

@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class CalendarResponseDto {

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
}
