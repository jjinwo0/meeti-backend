package yjhb.meeti.repository.approval;

import org.springframework.data.jpa.repository.JpaRepository;
import yjhb.meeti.domain.approval.entity.Approval;

public interface ApprovalRepository extends JpaRepository<Approval, Long> {
}
