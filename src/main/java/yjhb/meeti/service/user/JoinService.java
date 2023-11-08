package yjhb.meeti.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.service.user.UserService;

@Service
@RequiredArgsConstructor
@Transactional
public class JoinService {

    private final UserService userService;

    private final PasswordEncoder encoder;

    public String join(User user){

        user.hashPassword(encoder);

        userService.registerUser(user);

        return "Join Success";
    }
}
