package yjhb.meeti.repository.file;


import org.springframework.data.jpa.repository.JpaRepository;
import yjhb.meeti.domain.file.File;

public interface FileRepository extends JpaRepository<File, Long> {
}
