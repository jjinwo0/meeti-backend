package yjhb.meeti.service.chat;

import com.amazonaws.services.kms.model.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.domain.chat.ChatRoom;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.dto.chat.ChatRoomDto;
import yjhb.meeti.global.error.ErrorCode;
import yjhb.meeti.global.error.exception.EntityNotFoundException;
import yjhb.meeti.repository.chat.ChatRoomRepository;
import yjhb.meeti.service.user.UserService;

import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserService userService;

    @Transactional
    public Long createRoom(ChatRoomDto.CreateRequest request){

        ChatRoom createRoom = ChatRoom.builder()
                .admin(userService.findUserByUserId(request.getUserId()))
                .maxCount(request.getMaxCount())
                .roomName(request.getRoomName())
                .build();

        return chatRoomRepository.save(createRoom).getId();
    }

    @Transactional
    public void joinRoom(ChatRoomDto.JoinRequest request){

        ChatRoom findRoom = findByRoomId(request.getRoomId());
        User joinUser = userService.findUserByUserId(request.getJoinUserId());

        findRoom.addUser(joinUser);
    }

    public ChatRoom findByRoomId(Long roomId){

        return chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUNT_ROOM));
    }

    public List<ChatRoom> findByRoomName(String roomName){

        return chatRoomRepository.findChatRoomByRoomNameContaining(roomName);
    }

    public List<ChatRoom> findAll(){

        return chatRoomRepository.findAll();
    }
}
