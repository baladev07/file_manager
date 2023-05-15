package com.filemanager.dto;


import com.filemanager.model.FileEntity;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public class FileDetailsObject {

    private MultipartFile multipartFile;

    private String fileName;

    private String fileFormat;

    public FileDetailsObject(MultipartFile multipartFile)
    {
        this.multipartFile = multipartFile;
        String[] arr = multipartFile.getContentType().split("/");
        this.fileFormat = arr[1];
        this.fileName = multipartFile.getOriginalFilename();

    }

    String getFileName()
    {
        return this.fileName;
    }

    String getFileFormat()
    {
        return this.fileFormat;
    }

    public byte[] getBytes() throws IOException {
        return multipartFile.getBytes();
    }

    public String getOriginalFileName()
    {
        return multipartFile.getOriginalFilename();
    }

    public FileEntity getFileEntity()
    {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileFormat(fileFormat);
        fileEntity.setFileName(fileName);
        return fileEntity;
    }
}
