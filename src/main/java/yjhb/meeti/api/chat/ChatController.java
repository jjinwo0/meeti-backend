package yjhb.meeti.api.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.*;
import yjhb.meeti.dto.chat.ChatMessage;
import yjhb.meeti.global.jwt.service.TokenManager;
import yjhb.meeti.service.chat.ChatService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/meeti/chat")
@RequiredArgsConstructor
public class ChatController {

    private final TokenManager tokenManager;
    private final SimpUserRegistry simpUserRegistry;
    private final SimpMessageSendingOperations template;
    private final ChatService chatService;

    /**
     * 참가 User List 조회
     */
    @GetMapping("/users/info")
    public ResponseEntity<List> findJoinUsers(){

        Set<SimpUser> users = simpUserRegistry.getUsers();

        List<String> userList = users.stream()
                .map(u -> u.toString())
                .collect(Collectors.toList());

        return ResponseEntity.ok(userList);
    }

    /**
     * Post Chat
     */
    @PostMapping("/send")
    public void sendChat(StompHeaderAccessor accessor,
                         @RequestBody @Valid ChatMessage message){

        String authorization = accessor.getFirstNativeHeader("Authorization");
        String username = tokenManager.validateToken(authorization);
        message.setSourceName(username);

        log.info("chatMessage : {}", message);

        template.convertAndSend("/sub/"+message.getRoomId(), message);
    }
}
