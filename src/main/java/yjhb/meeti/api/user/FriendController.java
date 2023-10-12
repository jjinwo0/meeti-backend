package yjhb.meeti.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yjhb.meeti.domain.user.entity.Friend;
import yjhb.meeti.global.jwt.service.TokenManager;
import yjhb.meeti.global.resolver.memberinfo.UserInfoDto;
import yjhb.meeti.service.user.FriendService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/meeti/user")
@RequiredArgsConstructor
public class FriendController {

    private final TokenManager tokenManager;
    private final FriendService friendService;

    // 친구 요청 API
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

    @GetMapping("friend/request/{userId}")
    public ResponseEntity<List> searchWaitRequestFriendList(@PathVariable("userId") Long userId,
                                                            HttpServletRequest request){

        String authorization = request.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        List<Friend> requestList = friendService.findFriendRequestByToId(userId);

        return ResponseEntity.ok(requestList);
    }

    @GetMapping("/friend/search/{userId}")
    public ResponseEntity<List> searchFriend(@PathVariable("userId") Long userId,
                                             HttpServletRequest request){

        String authorization = request.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        List<UserInfoDto> findFriends = friendService.findFriendByUserId(userId);

        return ResponseEntity.ok(findFriends);
    }
}
