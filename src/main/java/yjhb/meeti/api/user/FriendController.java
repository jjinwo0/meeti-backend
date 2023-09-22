package yjhb.meeti.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yjhb.meeti.global.jwt.service.TokenManager;
import yjhb.meeti.service.user.FriendService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/meeti/user")
@RequiredArgsConstructor
public class FriendController {

    private final TokenManager tokenManager;
    private final FriendService friendService;

    @PostMapping("/friend/add/{userId}/{friendId}")
    public ResponseEntity<Boolean> addFriend(@PathVariable("userId") Long userId,
                                             @PathVariable("friendId") Long friendId,
                                             HttpServletRequest request){

        String authorization = request.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        friendService.addFriend(userId, friendId);

        return ResponseEntity.ok(true);
    }
}
