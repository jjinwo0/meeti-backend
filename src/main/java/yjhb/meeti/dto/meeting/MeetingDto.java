package yjhb.meeti.dto.meeting;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import yjhb.meeti.domain.meeting.Meeting;

import java.time.LocalDate;

public class MeetingDto {

    @Data
    public static class Request{
        private String title;

        private String detail;
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

        public static MeetingDto.Response from(Meeting meet){

            return MeetingDto.Response.builder()
                    .id(meet.getId())
                    .username(meet.getUser().getUsername())
                    .title(meet.getTitle())
                    .detail(meet.getDetail())
                    .date(meet.getDate())
                    .build();
        }
    }
}
