package yjhb.meeti.repository.approval;

import org.springframework.data.jpa.repository.JpaRepository;
import yjhb.meeti.domain.approval.entity.Approval;
import yjhb.meeti.domain.user.entity.User;

import java.util.List;

public interface ApprovalRepository extends JpaRepository<Approval, Long> {

    List<Approval> findByUser(User user);
}
