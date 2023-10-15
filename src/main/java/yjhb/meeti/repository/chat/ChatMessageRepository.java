package yjhb.meeti.repository.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import yjhb.meeti.domain.chat.ChatMessage;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findChatMessageBySender(String sender);
    List<ChatMessage> findChatMessageByMessageContaining(String message);
}
