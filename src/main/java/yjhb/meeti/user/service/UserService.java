package yjhb.meeti.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.global.error.ErrorCode;
import yjhb.meeti.global.error.exception.AuthenticationException;
import yjhb.meeti.user.constant.UserType;
import yjhb.meeti.user.entity.User;
import yjhb.meeti.user.entity.dto.JoinDTO;
import yjhb.meeti.user.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private void validateDuplicateUsername(User user){
        Optional<User> findUsers = userRepository.findByUsername(user.getUsername());

        if (!findUsers.isEmpty())
            throw new IllegalStateException("이미 존재하는 회원입니다.");
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Transactional
    public Long join(@Valid JoinDTO dto){
        User user = User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .role(dto.getRole())
                .profile(dto.getProfile())
                .build();

        validateDuplicateUsername(user);
        userRepository.save(user);

        return user.getId();
    }

    public Optional<User> findByUserId(Long userId){
        return userRepository.findById(userId);
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username).get();
    }

    public User findByPasswordWithUsername(String username, String password){
        User findUser = userRepository.findByUsername(username).stream()
                .filter(user -> user.getPassword().equals(password))
                .findAny().orElseThrow(() -> new EntityNotFoundException("회원 정보를 찾을 수 없습니다."));

        return findUser;
    }

    public UserType findUserTypeByUsernameAndPassword(String username, String password){
        return userRepository.findUserTypeByUsernameAndPassword(username, password);
    }
    @Transactional(readOnly = true)
    public User findUserByRefreshToken(String refreshToken) {

        User findMember = userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new AuthenticationException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));

        LocalDateTime tokenExpirationTime = findMember.getTokenExpirationTime(); // refresh token의 만료시간
        if (tokenExpirationTime.isBefore(LocalDateTime.now()))
            throw new AuthenticationException(ErrorCode.REFRESH_TOKEN_EXPIRED);

        return findMember;
    }

}
