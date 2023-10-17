package yjhb.meeti.api.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import yjhb.meeti.domain.chat.ChatRoom;
import yjhb.meeti.dto.chat.ChatRoomDto;
import yjhb.meeti.global.jwt.service.TokenManager;
import yjhb.meeti.service.chat.ChatRoomService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/meeti/chat")
@RequiredArgsConstructor
public class ChatRoomController {

    private final TokenManager tokenManager;
    private final ChatRoomService chatRoomService;

    /**
     * ChatRoom 생성
     */
    @PostMapping("/rooms/create")
    public ResponseEntity<?> createChatRoom(@RequestBody @Valid ChatRoomDto.CreateRequest dto,
                                            HttpServletRequest request){

        String authorization = request.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        Long roomId = chatRoomService.createRoom(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(roomId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    /**
     * ChatRoom 조회
     */
    @GetMapping("/rooms")
    public ResponseEntity<List> findRoomList(){

        return ResponseEntity.ok(chatRoomService.findAll());
    }

    /**
     * roomId으로 ChatRoom 조회
     */
    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<ChatRoom> findRoomByRoomId(@PathVariable("roomId")Long roomId){

        return ResponseEntity.ok(chatRoomService.findByRoomId(roomId));
    }

    /**
     * roomName으로 ChatRoom 조회
     */
    @GetMapping("/rooms/name")
    public ResponseEntity<List> findRoomByRoomName(@RequestParam String roomName){

        return ResponseEntity.ok(chatRoomService.findByRoomName(roomName));
    }

    /**
     * ChatRoom 입장
     */
    @PostMapping("/rooms/users")
    public ResponseEntity<Boolean> joinRoom(@RequestBody @Valid ChatRoomDto.JoinRequest dto,
                                   HttpServletRequest request){

        String authorization = request.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        chatRoomService.joinRoom(dto);

        return ResponseEntity.ok(true);
    }
}
