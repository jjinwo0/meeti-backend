package yjhb.meeti.domain.office.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import yjhb.meeti.domain.office.entity.Office;

import java.util.List;

public interface OfficeRepository extends JpaRepository<Office, Long> {

    List<Office> findByAddressContaining(String address);

    @Query("select o from Office o")
    List<Office> findAll();
}
