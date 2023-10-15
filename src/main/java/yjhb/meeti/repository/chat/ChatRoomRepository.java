package yjhb.meeti.repository.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import yjhb.meeti.domain.chat.ChatRoom;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    List<ChatRoom> findChatRoomByRoomNameContaining(String roomName);
}
