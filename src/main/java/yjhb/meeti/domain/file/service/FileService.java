package yjhb.meeti.domain.file.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import yjhb.meeti.api.file.upload.dto.FileDto;
import yjhb.meeti.domain.file.entity.File;
import yjhb.meeti.domain.file.repository.FileRepository;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class FileService {

    @Value("${part4.upload.path}")
    private String fileDir;
    private final FileRepository fileRepository;

    public Long save(MultipartFile files) throws IOException {

        if(files.isEmpty())
            return null;

        // 파일 이름 원본
        String originalFilename = files.getOriginalFilename();

        // 파일 이름으로 사용할 랜덤 uuid
        String uuid = UUID.randomUUID().toString();

        // 확장자 추출
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        // uuid와 확장자 결합
        String savedName = uuid + extension;

        // 파일을 불러올 때 사용할 파일 경로
        String savedPath = java.io.File.separator + fileDir;

        // 파일 엔티티 생성
        File file = FileDto.builder()
                .fileName(savedName)
                .filePath(savedPath)
                .build().toEntity();

        files.transferTo(new java.io.File(savedPath, savedName));

        fileRepository.save(file);

        return file.getId();
    }
}
