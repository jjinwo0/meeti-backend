package yjhb.meeti.api.file.upload.dto;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import yjhb.meeti.domain.file.entity.File;

@Data
@NoArgsConstructor
public class FileDto {

    private String content;
    private String fileName;
    private String filePath;

    public File toEntity(){
        return File.builder()
                .content(this.content)
                .fileName(this.fileName)
                .filePath(this.filePath)
                .build();
    }

    @Builder
    public FileDto(String content, String fileName, String filePath) {
        this.content = content;
        this.fileName = fileName;
        this.filePath = filePath;
    }
}
