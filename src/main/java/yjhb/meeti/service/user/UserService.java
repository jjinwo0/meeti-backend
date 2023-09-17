package yjhb.meeti.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.domain.user.constant.UserType;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.repository.user.UserRepository;
import yjhb.meeti.global.error.ErrorCode;
import yjhb.meeti.global.error.exception.AuthenticationException;
import yjhb.meeti.global.error.exception.BusinessException;
import yjhb.meeti.global.error.exception.EntityNotFoundException;
import yjhb.meeti.global.resolver.memberinfo.UserInfoDto;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User registerUser(User user){
        validateDuplicateUser(user);

        return userRepository.save(user);
    }

    private void validateDuplicateUser(User user) {
        Optional<User> findUser = userRepository.findByEmail(user.getEmail());
        if (findUser.isPresent())
            throw new BusinessException(ErrorCode.ALREADY_REGISTERED_MEMBER);
    }

    public Optional<User> findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public User findUserByRefreshToken(String refreshToken){
        User findUser = userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new AuthenticationException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));

        LocalDateTime tokenExpirationTime = findUser.getTokenExpirationTime();
        if (tokenExpirationTime.isBefore(LocalDateTime.now()))
            throw new AuthenticationException(ErrorCode.REFRESH_TOKEN_EXPIRED);

        return findUser;
    }

    public User findUserByUserId(Long id){

        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_EXISTS));
    }

    public User findByEmailAndPassword(String email, String password){

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_EXISTS_EMAIL));

        if (!user.getPassword().equals(password))
            throw new EntityNotFoundException(ErrorCode.NOT_EXISTS_PASSWORD);

        return user;
    }

    public UserType findUserTypeByUsernameAndPassword(String email, String password){
        return userRepository.findUserTypeByEmailAndPassword(email, password);
    }

    public UserInfoDto findUserInfo(Long id){
        Optional<User> findUser = userRepository.findById(id);
        if (findUser.isEmpty())
            throw new EntityNotFoundException(ErrorCode.USER_NOT_EXISTS);

        return UserInfoDto.builder()
                .id(id)
                .username(findUser.get().getUsername())
                .profile(findUser.get().getProfile())
                .role(findUser.get().getRole())
                .build();
    }
}
