package yjhb.meeti.domain.file.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import yjhb.meeti.domain.file.entity.File;

public interface FileRepository extends JpaRepository<File, Long> {
}
