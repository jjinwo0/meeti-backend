package yjhb.meeti.api.file.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yjhb.meeti.domain.file.service.FileService;
import yjhb.meeti.global.jwt.service.TokenManager;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Tag(name = "File Upload", description = "파일 업로드 API")
@RestController
@RequestMapping("meeti/file")
@RequiredArgsConstructor
public class FileUploadController {

    private final TokenManager tokenManager;
    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file,
                                    HttpServletRequest httpServletRequest) throws IOException {

        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        fileService.save(file);

        return ResponseEntity.ok("File Upload Success");
    }
}
