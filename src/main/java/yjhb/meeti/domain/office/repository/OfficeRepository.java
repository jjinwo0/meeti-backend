package yjhb.meeti.domain.office.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yjhb.meeti.domain.office.entity.Office;

import java.util.List;

public interface OfficeRepository extends JpaRepository<Office, Long> {

    List<Office> findOfficeByAddress(String address);
}
