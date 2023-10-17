package yjhb.meeti.dto.chat;

import com.sun.security.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.user.SimpSubscription;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.DefaultSimpUserRegistry;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import yjhb.meeti.global.jwt.service.TokenManager;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class JoinRoomEventService {

    private final SimpMessageSendingOperations template;
    private final SimpUserRegistry simpUserRegistry;
    private final TokenManager tokenManager;

    @EventListener
    public void handleSubscribeEvent(SessionSubscribeEvent event){

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

        String authorization = accessor.getFirstNativeHeader("Authorization");
        String username = tokenManager.validateToken(authorization);
        String destination = accessor.getFirstNativeHeader(StompHeaderAccessor.STOMP_DESTINATION_HEADER);

        UserPrincipal userPrincipal = new UserPrincipal(username);
        ((DefaultSimpUserRegistry) simpUserRegistry).onApplicationEvent(new SessionConnectedEvent(this, event.getMessage(), userPrincipal));
        ((DefaultSimpUserRegistry) simpUserRegistry).onApplicationEvent(new SessionSubscribeEvent(this, event.getMessage(), userPrincipal));

        Set<SimpSubscription> subscriptions = simpUserRegistry.findSubscriptions(subscription ->
                subscription.getDestination().equals(destination));

        List<String> usernames = subscriptions.stream()
                .map(sub -> sub.getSession().getUser().getName())
                .collect(Collectors.toList());

        JoinServerMessage serverMessage = JoinServerMessage.builder()
                .sourceName(EventProperties.SERVER_NAME)
                .content(String.format("%s님이 입장하였습니다.", username))
                .usernames(usernames)
                .build();

        log.info("server 입장 : {}", serverMessage);

        accessor.getSessionAttributes().put(EventProperties.SESSION_USERNAME, username);
        accessor.getSessionAttributes().put(EventProperties.SESSION_DESTINATION, destination);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        template.convertAndSend(destination, serverMessage);
    }
}
