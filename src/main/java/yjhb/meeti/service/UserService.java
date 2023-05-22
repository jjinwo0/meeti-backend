package yjhb.meeti.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.dto.UserDTO;
import yjhb.meeti.entity.User;
import yjhb.meeti.repository.UserRepository;

import javax.validation.Valid;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private void validateDuplicateUser(User user){
        Optional<User> findUsers = userRepository.findById(user.getId());

        if (!findUsers.isEmpty())
            throw new IllegalStateException("이미 존재하는 회원입니다.");
    }

    public Long join(@Valid UserDTO dto){
        User user = User.builder().dto(dto).build();

        validateDuplicateUser(user);
        userRepository.save(user);

        return user.getId();
    }
}
