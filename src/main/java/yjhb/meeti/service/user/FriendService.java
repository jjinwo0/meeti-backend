package yjhb.meeti.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.domain.user.entity.Friend;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.global.resolver.memberinfo.UserInfoDto;
import yjhb.meeti.repository.user.FriendRepository;

import java.util.ArrayList;
import java.util.List;

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

    public List<UserInfoDto> findFriendByUserId(Long userId){

        User findUser = userService.findUserByUserId(userId);

        List<Friend> friends = findUser.getFriends();
        List<UserInfoDto> friendDtoList = new ArrayList<>();

        for (Friend f : friends){

            User findFriend = userService.findUserByUserId(f.getFId());

            friendDtoList.add(
                    UserInfoDto.builder()
                        .id(findFriend.getId())
                        .username(findFriend.getUsername())
                        .profile(findFriend.getProfile())
                        .role(findFriend.getRole())
                        .build()
            );
        }

        return friendDtoList;
    }
}
