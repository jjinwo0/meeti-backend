package yjhb.meeti.dto.chat;

import lombok.Data;
import yjhb.meeti.domain.chat.ChatRoom;

public class ChatRoomDto {

    @Data
    public static class Request{

        private String roomName;

        public ChatRoom toEntity(){

            return ChatRoom.builder()
                    .roomName(this.roomName)
                    .build();
        }
    }
}
