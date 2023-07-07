package yjhb.meeti.api.registration.calender.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class CalenderRegDto {

    private String title;
    private String color;
    private Date start;
    private Date end;
}
