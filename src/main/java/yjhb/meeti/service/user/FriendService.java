package yjhb.meeti.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.domain.user.entity.Friend;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.global.resolver.memberinfo.UserInfoDto;
import yjhb.meeti.repository.user.FriendRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FriendService {

    private final UserService userService;
    private final FriendRepository friendRepository;

    // 친구 요청 메서드
    @Transactional
    public void addFriend(Long userId, Long friendId){

        User findUser = userService.findUserByUserId(friendId);

        Friend friend = Friend.builder()
                .user(findUser) // 요청을 받는 사람
                .fromId(userId) // 요청을 보내는 사람의 id
                .favorite(false)
                .permit(false)
                .build();

        friendRepository.save(friend);
    }

    // 요청 승인 메서드
    @Transactional
    public void permitFriend(Long userId, Long fromId){
        // todo
    }

    public List<UserInfoDto> findFriendByUserId(Long userId){

        User findUser = userService.findUserByUserId(userId);

        List<Friend> friends = findUser.getFriends();
        List<UserInfoDto> friendDtoList = new ArrayList<>();

        for (Friend f : friends){

            User findFriend = userService.findUserByUserId(f.getFromId());

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

    // 요청 승인 대기 목록 조회 메서드
    public List<Friend> findFriendRequestByToId(Long userId){

        return friendRepository.findByToId(userId).stream()
                .filter(f -> !f.isPermit())
                .collect(Collectors.toList());
    }
}
