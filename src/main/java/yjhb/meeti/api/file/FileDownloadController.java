package yjhb.meeti.api.file;

import feign.template.UriUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yjhb.meeti.domain.file.File;
import yjhb.meeti.service.file.FileService;
import yjhb.meeti.global.jwt.service.TokenManager;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
@Tag(name = "File Download", description = "파일 다운로드 API")
@RestController
@RequestMapping("/meeti/file")
@RequiredArgsConstructor
public class FileDownloadController {

    private final FileService fileService;
    private final TokenManager tokenManager;

    @Tag(name = "File Download")
    @GetMapping("/downloads/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileId") Long id,
                                                 HttpServletRequest httpServletRequest) throws MalformedURLException{

        String authorization = httpServletRequest.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        File findFile = fileService.findById(id);

        UrlResource resource = new UrlResource("file: " + findFile.getFilePath());

        String encodedFileName = UriUtils.encode(findFile.getFileName(), StandardCharsets.UTF_8);

        String contentDisposition = "attachment; filename=\"" + encodedFileName + "\"";

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition).body(resource);
    }
}
