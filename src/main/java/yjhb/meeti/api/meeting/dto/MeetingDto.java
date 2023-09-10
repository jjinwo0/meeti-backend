package yjhb.meeti.api.meeting.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

public class MeetingDto {

    @Data
    public static class Request{
        private String title;

        private String detail;

//        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
//        private LocalDate date;
    }

    @Data
    public static class Response{

        private Long id;

        private String title;

        private String username;

        private String detail;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private LocalDate date;

        @Builder
        public Response(Long id, String title, String username, String detail, LocalDate date) {
            this.id = id;
            this.title = title;
            this.username = username;
            this.detail = detail;
            this.date = date;
        }
    }
}
