package com.filemanager.service;

import com.filemanager.dto.FileDetailsDTO;
import com.filemanager.model.DirectoryEntity;
import com.filemanager.model.FileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    void upload(FileDetailsDTO fileDetailsObject) throws Exception;

    void delete(long id) throws Exception;

    FileEntity getFile(long id);

    List getFilesList();

    List getFilesListInDirectory(DirectoryEntity directoryEntity);

    void downloadFile(long id);
}
