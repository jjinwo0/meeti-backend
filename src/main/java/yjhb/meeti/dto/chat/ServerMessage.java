package yjhb.meeti.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yjhb.meeti.domain.chat.MessageType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ServerMessage {

    private String sourceName;
    @NotEmpty
    @Size(min = 1, max = 300)
    private String message;
    private MessageType messageType;
}
