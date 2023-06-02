package yjhb.meeti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import yjhb.meeti.entity.Calender;
import yjhb.meeti.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u")
    List<User> findAll();

    @Query("select u from User u where u.username =: username")
    Optional<User> findByUsername(String username);

    @Query("select u.calenders from User u where u.id =: id")
    List<Calender> findScheduleByUserId(Long id);
}
