package yjhb.meeti.service.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import yjhb.meeti.domain.user.constant.Role;
import yjhb.meeti.domain.user.constant.UserType;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.global.error.exception.BusinessException;
import yjhb.meeti.repository.user.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    static User user1;
    static User user2;

    @BeforeEach
    void 유저_생성(){

        userRepository.deleteAll();

        user1 = User.builder()
                .username("test1")
                .role(Role.COMMON)
                .email("test1@gmail.com")
                .password("1234")
                .userType(UserType.COMMON)
                .build();

        user2 = User.builder()
                .username("test2")
                .role(Role.ADMIN)
                .email("test2@gmail.com")
                .password("1234")
                .userType(UserType.COMMON)
                .build();

        userService.registerUser(user1);
        userService.registerUser(user2);
    }

    @Test
    @DisplayName("유저 Email 중복 확인")
    void 유저_중복() throws Exception{

        User test = User.builder()
                .email("test1@gmail.com")
                .build();

        assertThrows(BusinessException.class, () -> {
            userService.registerUser(test);
        });
    }

    @Test
    @DisplayName("유저 id 검색")
    void 유저_ID_검색(){

        Long user1Id = user1.getId();
        User findUser = userService.findUserByUserId(user1Id);

        assertThat(user1.getId()).isEqualTo(findUser.getId());
    }

    @Test
    @DisplayName("유저 Email Password 검색")
    void 유저_Email_Password_검색(){

        String email = user1.getEmail();
        String password = user1.getPassword();

        User findUser = userService.findByEmailAndPassword(email, password);

        assertThat(user1.getId()).isEqualTo(findUser.getId());
    }
}