package yjhb.meeti.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import yjhb.meeti.domain.user.entity.Friend;

public interface FriendRepository extends JpaRepository<Friend, Long> {
}
