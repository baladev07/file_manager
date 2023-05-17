package com.filemanager.controller;



import com.filemanager.Util.SuccessMessageConstants;
import com.filemanager.dto.EntityResponseBuilder;
import com.filemanager.dto.FileDetailsDTO;

import com.filemanager.file.FileManager;
import com.filemanager.http.HttpResponseBuilder;
import com.filemanager.model.FileEntity;
import com.filemanager.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.Arrays;


@RestController
@Validated
@RequestMapping("/api")
public class FileAPI {

    @Autowired
    FileService fileService;

    @Autowired HttpResponseBuilder httpResponseBuilder;

    @Autowired
    EntityResponseBuilder entityResponseBuilder;

    @PostMapping("/upload")
    public ResponseEntity uploadFile(FileDetailsDTO file) throws Exception {
        fileService.upload(file);
        return ResponseEntity.ok(httpResponseBuilder.message(SuccessMessageConstants.FILE_UPLOADED).data(entityResponseBuilder.getResponseDTO()).build());
    }

    @GetMapping("/file/{id}")
    public ResponseEntity getFileDetail(@PathVariable @Pattern(regexp = "\\d+", message = "Invalid ID")String id)
    {
        fileService.getFile(Long.parseLong(id));
        return ResponseEntity.ok(httpResponseBuilder.message("success").data("file",entityResponseBuilder.getResponseDTO()).build());
    }

    @DeleteMapping("/file/{id}")
    public ResponseEntity deleteFile(@PathVariable @Pattern(regexp = "\\d+", message = "Invalid ID") String id) throws Exception {
        fileService.delete(Long.parseLong(id));
        return ResponseEntity.ok(httpResponseBuilder.message(SuccessMessageConstants.FILE_DELETED).build());
    }

    @GetMapping("/files")
    public ResponseEntity getFilesList()
    {
        return ResponseEntity.ok(httpResponseBuilder.message("success").data("directories",fileService.getFilesList()).build());
    }

    @GetMapping("/file/{id}/download")
    public ResponseEntity downloadFile(@PathVariable @Pattern(regexp = "\\d+", message = "Invalid ID")String id)
    {
        FileEntity fileEntity = fileService.getFile(Long.parseLong(id));
        byte[] bytes = FileManager.downloadFile(fileEntity.getFilePath());
        return ResponseEntity.ok().
                header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=\""+fileEntity.getFileNameWithFormat()+"\"")
                .body(new ByteArrayResource(bytes));
    }
}
