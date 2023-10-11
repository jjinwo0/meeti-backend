package yjhb.meeti.api.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import yjhb.meeti.domain.user.constant.Role;
import yjhb.meeti.domain.user.constant.UserType;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.dto.user.LoginDTO;
import yjhb.meeti.repository.user.UserRepository;
import yjhb.meeti.service.user.UserService;

@SpringBootTest
class UserControllerTest {

    @Autowired
    LoginController loginController;

    @Autowired
    LogoutController logoutController;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    static User user1;

    @BeforeEach
    void 유저_생성(){

        userRepository.deleteAll();

        user1 = User.builder()
                .username("test1")
                .role(Role.ADMIN)
                .email("test1@gmail.com")
                .password("1234")
                .userType(UserType.COMMON)
                .build();

        LoginDTO.Request dto = new LoginDTO.Request();
        dto.setEmail(user1.getEmail());
        dto.setPassword(user1.getPassword());
    }

//    @Test
//    @DisplayName("로그인 처리")
//    void 유저_로그인(){
//        loginController.login()
//    }

}