package yjhb.meeti.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.domain.user.entity.Friend;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.repository.user.FriendRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FriendService {

    private final UserService userService;
    private final FriendRepository friendRepository;

    public void addFriend(Long userId, Long friendId){

        User findUser = userService.findUserByUserId(userId);

        Friend friend = Friend.builder()
                .user(findUser)
                .fId(friendId)
                .favorite(false)
                .build();

        findUser.getFriends().add(friend);
    }
}
