package yjhb.meeti.dto.chat;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {

    @NotNull
    private Long roomId;

    private String sourceName;

    @NotEmpty
    @Size(max = 300, min = 1)
    private String message;

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
