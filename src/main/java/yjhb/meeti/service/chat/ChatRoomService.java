package yjhb.meeti.service.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.domain.chat.ChatRoom;
import yjhb.meeti.dto.chat.ChatRoomDto;
import yjhb.meeti.repository.chat.ChatRoomRepository;

import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public Long createRoom(ChatRoomDto.Request request){

        return chatRoomRepository.save(request.toEntity()).getId();
    }

    public List<ChatRoom> findByRoomName(String roomName){

        return chatRoomRepository.findChatRoomByRoomNameContaining(roomName);
    }
}
