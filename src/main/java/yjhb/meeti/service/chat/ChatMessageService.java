package yjhb.meeti.service.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import yjhb.meeti.dto.chat.ChatMessageDto;
import yjhb.meeti.repository.chat.ChatMessageRepository;
import yjhb.meeti.service.user.UserService;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatMessageService {

    private final SimpMessagingTemplate template;
    private final UserService userService;
    private final ChatMessageRepository chatMessageRepository;

    public void sendMessage(String type, Long roomId, Long userId, String message){

        template.convertAndSend(
                "/sub/chat/"+message,
                ChatMessageDto.Response.builder()
                        .);
    }
}
