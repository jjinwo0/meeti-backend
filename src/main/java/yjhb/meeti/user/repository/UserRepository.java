package yjhb.meeti.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import yjhb.meeti.user.constant.UserType;
import yjhb.meeti.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u")
    List<User> findAll();

//    @Query("select u from User u where u.username =: username")
    Optional<User> findByUsername(@Param("username") String username);

    Optional<User> findByEmail(String email);
    Optional<User> findByRefreshToken(String refreshToken);
    @Query("select u.userType from User u where u.username =: username and u.password =: password")
    UserType findUserTypeByUsernameAndPassword(String username, String password);
}
