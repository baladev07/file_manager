package com.filemanager.dto;


import com.filemanager.model.DirectoryEntity;
import com.filemanager.model.FileEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Value
@Builder
public class FileDetailsDTO {

    public static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "pdf", "txt", "mov");

    MultipartFile multipartFile;

    String dirName;

    public String getFileNameWithFormat()
    {
        return multipartFile.getOriginalFilename();
    }

    public String getFileFormat()
    {
        String fileName = multipartFile.getOriginalFilename();
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return fileExtension;
    }

    public byte[] getBytes() throws IOException {
        return multipartFile.getBytes();

    }

    public long getSize()
    {
        return multipartFile.getSize();
    }

    public String getFileName()
    {
        String fileFormat = "."+getFileFormat();
        String fileName = multipartFile.getOriginalFilename().replace(fileFormat, "");
        return fileName;
    }

    public String getDirName()
    {
        return this.dirName;
    }
}
