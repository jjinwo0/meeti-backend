package yjhb.meeti.repository.message;

import org.springframework.data.jpa.repository.JpaRepository;
import yjhb.meeti.domain.message.Message;
import yjhb.meeti.domain.user.entity.User;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findBySender(User sender);

    List<Message> findByReceiver(User receiver);
}
