package yjhb.meeti.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.service.user.UserService;

@Service
@RequiredArgsConstructor
@Transactional
public class JoinService {

    private final UserService userService;

    public String join(User user){
        userService.registerUser(user);

        return "Join Success";
    }
}
