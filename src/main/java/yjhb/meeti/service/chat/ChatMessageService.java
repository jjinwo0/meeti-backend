package yjhb.meeti.service.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.dto.chat.ChatMessageDto;
import yjhb.meeti.repository.chat.ChatMessageRepository;
import yjhb.meeti.service.user.UserService;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatMessageService {

    private final SimpMessagingTemplate template;
    private final UserService userService;
    private final ChatMessageRepository chatMessageRepository;

    @Transactional
    public void sendMessage(String type, Long roomId, Long userId, String message){


    }
}
