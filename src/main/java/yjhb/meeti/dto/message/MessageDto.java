package yjhb.meeti.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import yjhb.meeti.domain.message.Message;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {

    private String title;

    private String content;

    private String senderName;

    private String receiverName;

    public MessageDto from(Message message){

        return MessageDto.builder()
                .title(message.getTitle())
                .content(message.getContent())
                .senderName(message.getSender().getUsername())
                .receiverName(message.getReceiver().getUsername())
                .build();
    }
}
