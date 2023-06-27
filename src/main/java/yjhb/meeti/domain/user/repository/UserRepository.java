package yjhb.meeti.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import yjhb.meeti.domain.user.constant.UserType;
import yjhb.meeti.domain.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByRefreshToken(String refreshToken);
    @Query("select u from User u where u.email =: email and u.password =: password")
    Optional<User> findByPasswordWithUsername(String email, String password);
    UserType findUserTypeByEmailAndPassword(String email, String password);
}

