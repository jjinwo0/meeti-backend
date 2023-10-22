package yjhb.meeti.repository.office;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import yjhb.meeti.domain.office.Office;

import java.util.List;
import java.util.Optional;

public interface OfficeRepository extends JpaRepository<Office, Long> {

    List<Office> findByAddressContaining(String address);

    List<Office> findByPlaceNameContaining(String placeName);

    @Query("select o from Office o")
    List<Office> findAll();
}
