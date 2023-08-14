package yjhb.meeti.api.file.upload.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import yjhb.meeti.api.file.upload.dto.FileDto;
import yjhb.meeti.domain.file.service.FileService;
import yjhb.meeti.global.jwt.service.TokenManager;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "File Upload", description = "파일 업로드 API")
@RestController
@RequestMapping("meeti/file")
@RequiredArgsConstructor
public class FileUploadController {

    private final TokenManager tokenManager;
    private final FileService fileService;
    @Value("${part4.upload.path}")
    private String uploadDir;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam MultipartFile uploadFile,
                                 HttpServletRequest httpServletRequest){

        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        String fullPath = uploadDir + uploadFile.getOriginalFilename();

        FileDto fileDto = FileDto.builder()
                .fileName(uploadFile.getOriginalFilename())
                .filePath(fullPath)
                .build();

        fileService.save(fileDto);

        return ResponseEntity.ok("File Upload Success");
    }
}
