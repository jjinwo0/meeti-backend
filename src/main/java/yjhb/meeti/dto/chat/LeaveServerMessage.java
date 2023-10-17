package yjhb.meeti.dto.chat;

import lombok.Builder;
import lombok.Getter;
import yjhb.meeti.domain.chat.MessageType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
public class LeaveServerMessage extends ServerMessage{

    private String leavedUserName;

    @Builder
    public LeaveServerMessage(String sourceName, @NotEmpty @Size(min = 1, max = 300) String content, MessageType messageType, String leavedUserName) {

        super(sourceName, content, messageType);
        this.leavedUserName = leavedUserName;
    }
}
