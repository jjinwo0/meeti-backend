package yjhb.meeti.dto.chat;

import lombok.Data;
import yjhb.meeti.domain.chat.ChatRoom;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ChatRoomDto {

    @Data
    public static class CreateRequest{

        private String roomName;

        @Min(2) @Max(50)
        private int maxCount;

        @NotNull
        Long userId;
    }

    @Data
    public static class JoinRequest{

        private Long roomId;

        private Long joinUserId;
    }
}
