package yjhb.meeti.repository.message;

import org.springframework.data.jpa.repository.JpaRepository;
import yjhb.meeti.domain.message.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
