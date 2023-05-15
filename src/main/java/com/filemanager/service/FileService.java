package com.filemanager.service;

import com.filemanager.dto.FileDetailsObject;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    void store(FileDetailsObject fileDetailsObject) throws Exception;

    void delete();

    void getFile();


}
