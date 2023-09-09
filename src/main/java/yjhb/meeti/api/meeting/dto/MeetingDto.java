package yjhb.meeti.api.meeting.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

public class MeetingDto {

    @Data
    public static class Request{
        private String title;

        private String detail;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private LocalDate date;
    }
}
