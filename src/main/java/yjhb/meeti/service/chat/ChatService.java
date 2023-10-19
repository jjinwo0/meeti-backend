package yjhb.meeti.service.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.dto.chat.ChatMessage;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatService {

    @Transactional
    public void createChat(ChatMessage chatMessage) {

    }

}