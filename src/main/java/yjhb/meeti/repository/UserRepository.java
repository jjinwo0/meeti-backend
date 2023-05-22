package yjhb.meeti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yjhb.meeti.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
