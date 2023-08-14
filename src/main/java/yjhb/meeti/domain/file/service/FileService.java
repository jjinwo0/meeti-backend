package yjhb.meeti.domain.file.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjhb.meeti.api.file.upload.dto.FileDto;
import yjhb.meeti.domain.file.entity.File;
import yjhb.meeti.domain.file.repository.FileRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class FileService {

    private final FileRepository fileRepository;

    public Long save(FileDto fileDto){
        return fileRepository.save(fileDto.toEntity()).getId();
    }
}
