package yjhb.meeti.api.file.upload.dto;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import yjhb.meeti.domain.file.entity.File;

@Data
@NoArgsConstructor
public class FileDto {

    private String fileName;
    private String filePath;

    public File toEntity(){
        return File.builder()
                .fileName(this.fileName)
                .filePath(this.filePath)
                .build();
    }

    @Builder

    public FileDto(String fileName, String content, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }
}
