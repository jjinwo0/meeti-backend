package yjhb.meeti.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.global.jwt.dto.JwtTokenDto;
import yjhb.meeti.global.jwt.service.TokenManager;
import yjhb.meeti.user.dto.LoginDTO;
import yjhb.meeti.user.dto.UserDTO;
import yjhb.meeti.calender.entity.Calender;
import yjhb.meeti.user.entity.User;
import yjhb.meeti.service.jwt.JwtService;
import yjhb.meeti.user.repository.UserRepository;
import yjhb.meeti.service.mail.MailService;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final TokenManager tokenManager;


    private void validateDuplicateUsername(User user){
        Optional<User> findUsers = userRepository.findByUsername(user.getUsername());

        if (!findUsers.isEmpty())
            throw new IllegalStateException("이미 존재하는 회원입니다.");
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Transactional
    public Long join(@Valid UserDTO dto){
        User user = User.builder().dto(dto).build();

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

    @Transactional
    public Long update(@Valid UserDTO dto, HttpSession session){
        User loginUser = (User) session.getAttribute("loginUser");

        User updateUser = loginUser.update(dto);
        userRepository.save(updateUser);

        return updateUser.getId();
    }

    public String login(@Valid LoginDTO dto){

        User findUser = userRepository.findByUsername(dto.getUsername())
                .stream().filter(u -> u.getPassword().equals(dto.getPassword()))
                .findFirst().orElseThrow(() -> new IllegalStateException("해당 회원정보를 찾을 수 없습니다."));

        JwtTokenDto jwtTokenDto = tokenManager.createJwtTokenDto(findUser.getId(), findUser.getRole());

        return TokenManager.createAccessToken(findUser.getId(), findUser.getRole(), jwtTokenDto.getAccessTokenExpireTime());
    }

    public List<Calender> findSchedule(Long userId){

        User findUser = userRepository.findById(userId).get();

        return findUser.getCalenders();
    }

}
