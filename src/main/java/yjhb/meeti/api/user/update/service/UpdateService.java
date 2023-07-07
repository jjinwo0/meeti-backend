package yjhb.meeti.api.user.update.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.domain.user.service.UserService;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateService {

    private final UserService userService;

    public Long updateUser(Long userId, String username, String password){
        User findUser = userService.findUserByUserId(userId);
        User updateUser = findUser.update(username, password);

        return updateUser.getId();
    }
}
