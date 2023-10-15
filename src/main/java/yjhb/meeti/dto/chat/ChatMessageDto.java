package yjhb.meeti.dto.chat;

import lombok.Builder;
import lombok.Data;

public class ChatMessageDto {

    @Data
    public static class Request{

        private String message;
    }

    @Data
    public static class Response{

        private Long id;
        private String sender;
        private String type;
        private String value;

        @Builder
        public Response(Long id, String sender, String type, String value) {
            this.id = id;
            this.sender = sender;
            this.type = type;
            this.value = value;
        }
    }
}
