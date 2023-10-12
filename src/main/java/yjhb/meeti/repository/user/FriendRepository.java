package yjhb.meeti.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import yjhb.meeti.domain.user.entity.Friend;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    @Query("select f from Friend f where f.user.id = :id")
    List<Friend> findByToId(@Param("id") Long id);


    @Query("select f from Friend f where f.user.id = :toId and f.fromId = :fromId")
    Optional<Friend> findByFromId(@Param("toId") Long toId, @Param("fromId") Long fromId);
}
