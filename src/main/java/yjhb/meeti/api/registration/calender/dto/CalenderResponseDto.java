package yjhb.meeti.api.registration.calender.dto;

import lombok.*;

import java.util.Date;

@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class CalenderResponseDto {

    private String title;
    private String color;
    private Date start;
    private Date end;
}
