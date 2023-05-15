package com.filemanager.controller;


import com.filemanager.Util.DirectoryUtils;
import com.filemanager.dto.FileDetailsObject;
import com.filemanager.http.HttpResponseBuilder;
import com.filemanager.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class FileAPI {

    @Autowired
    FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity uploadFile(@RequestPart("file") MultipartFile multipartFile) throws Exception {
        fileService.store(new FileDetailsObject(multipartFile));
        return ResponseEntity.ok(new HttpResponseBuilder().message("File successfully uploaded").build());
    }
}
