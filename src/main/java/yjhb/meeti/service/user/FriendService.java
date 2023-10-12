package yjhb.meeti.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.domain.user.entity.Friend;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.global.error.ErrorCode;
import yjhb.meeti.global.error.exception.BusinessException;
import yjhb.meeti.global.error.exception.EntityNotFoundException;
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

    public Friend validFriend(Long toId, Long fromId){

        Friend findFriend = friendRepository.findByFromId(toId, fromId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.FRIEND_NOT_FOUND));

        if (!findFriend.isPermit())
            throw new BusinessException(ErrorCode.FRIEND_NOT_PERMIT);

        return findFriend;
    }

    // 친구 요청 메서드
    @Transactional
    public void addFriend(Long fromId, Long toId){

        User findUser = userService.findUserByUserId(toId);

        Friend friend = Friend.builder()
                .user(findUser) // 요청을 받는 사람
                .fromId(fromId) // 요청을 보내는 사람의 id
                .favorite(false)
                .permit(false)
                .build();

        friendRepository.save(friend);
    }

    // 요청 승인 메서드
    @Transactional
    public void permitFriend(Long toId, Long fromId){

        Friend findFriend = friendRepository.findByFromId(toId, fromId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.FRIEND_NOT_FOUND));

        // 만약 이미 처리된 건이라면,
        if (findFriend.isPermit())
            throw new BusinessException(ErrorCode.FRIEND_ALREADY_PERMIT);

        findFriend.acceptPermit(); // 요청 수락 (친구 관계 허가 완료)

        // 양방향 설정을 위해 객체 추가
        Friend friend = Friend.builder()
                .user(userService.findUserByUserId(fromId)) // 요청을 보냈던 사람
                .fromId(toId) // 요청을 받은 사람
                .favorite(false)
                .permit(true) // 요청 수락 상태로 빌드
                .build();

        friendRepository.save(friend);
    }

    // 즐겨찾기 T/F 메서드
    @Transactional
    public void changeFavorite(Long toId, Long fromId){

        Friend findFriend = validFriend(toId, fromId);

        findFriend.updateFavorite();
    }

    public List<UserInfoDto> findFriendByUserId(Long userId){

        User findUser = userService.findUserByUserId(userId);

        return findUser.getFriends().stream()
                .map(f -> userService.findUserByUserId(f.getFromId()))
                .map(findFriend -> UserInfoDto.builder()
                        .id(findFriend.getId())
                        .username(findFriend.getUsername())
                        .profile(findFriend.getProfile())
                        .role(findFriend.getRole())
                        .build())
                .collect(Collectors.toList());
    }

    // 요청 승인 대기 목록 조회 메서드
    public List<Friend> findFriendRequestByToId(Long userId){

        return friendRepository.findByToId(userId).stream()
                .filter(f -> !f.isPermit())
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteFriend(Long userId, Long friendId) {

        Friend findFromId = friendRepository.findByFromId(userId, friendId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.FRIEND_NOT_FOUND));

        Friend findToId = friendRepository.findByFromId(friendId, userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.FRIEND_NOT_FOUND));

        friendRepository.delete(findFromId);
        friendRepository.delete(findToId);
    }
}
