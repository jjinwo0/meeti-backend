package yjhb.meeti.api.message;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.dto.message.MessageDto;
import yjhb.meeti.global.jwt.service.TokenManager;
import yjhb.meeti.service.message.MessageService;
import yjhb.meeti.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/meeti/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;
    private final TokenManager tokenManager;

    @PostMapping("/send/{receiverId}")
    public ResponseEntity<Boolean> sendMessage(@RequestBody MessageDto messageDto,
                                               @PathVariable Long receiverId,
                                               HttpServletRequest request){

        String authorization = request.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        User sender = userService.findUserByUserId(tokenManager.getUserIdFromClaims(accessToken));
        User receiver = userService.findUserByUserId(receiverId);

        messageService.write(sender, receiver, messageDto);

        return ResponseEntity.ok(true);
    }

    @GetMapping("/received")
    public ResponseEntity<List> findReceivedMessages(HttpServletRequest request){

        String authorization = request.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        User receiver = userService.findUserByUserId(tokenManager.getUserIdFromClaims(accessToken));

        return ResponseEntity.ok(messageService.receiveMessageList(receiver));
    }

    @GetMapping("/sent")
    public ResponseEntity<List> findSentMessages(HttpServletRequest request){

        String authorization = request.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        User sender = userService.findUserByUserId(tokenManager.getUserIdFromClaims(accessToken));

        return ResponseEntity.ok(messageService.sentMessage(sender));
    }

    @DeleteMapping("/received/delete/{messageId}")
    public ResponseEntity<Boolean> deleteReceivedMessage(@PathVariable("messageId") Long messageId,
                                                 HttpServletRequest request){

        String authorization = request.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);
        User receiver = userService.findUserByUserId(tokenManager.getUserIdFromClaims(accessToken));

        messageService.deleteMessageByReceiver(messageId, receiver);

        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/sent/delete/{messageId}")
    public ResponseEntity<Boolean> deleteSentMessage(@PathVariable("messageId") Long messageId,
                                                 HttpServletRequest request){

        String authorization = request.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);
        User sender = userService.findUserByUserId(tokenManager.getUserIdFromClaims(accessToken));

        messageService.deleteMessageByReceiver(messageId, sender);

        return ResponseEntity.ok(true);
    }
}
