package yjhb.meeti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import yjhb.meeti.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u")
    List<User> findAll();

    @Query("select u from User u where u.username =: username")
    List<User> findByUsername(String username);
}
