package yjhb.meeti.dto.chat;

import lombok.Builder;
import lombok.Getter;
import yjhb.meeti.domain.chat.MessageType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
public class JoinServerMessage extends ServerMessage{

    private List<String> usernames = new ArrayList<>();

    @Builder
    public JoinServerMessage(String sourceName, @NotEmpty @Size(min = 1, max = 300) String content, MessageType messageType, List<String> usernames){

        super(sourceName, content, messageType);
        this.usernames = usernames;
    }
}
