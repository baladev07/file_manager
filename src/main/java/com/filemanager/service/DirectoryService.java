package com.filemanager.service;

import com.filemanager.dto.DirectoryRequestDTO;
import com.filemanager.model.DirectoryEntity;
import com.filemanager.model.FileEntity;

import java.util.List;

public interface DirectoryService{


    DirectoryEntity createDirectory(String dirName,String dirPath) throws Exception;

    void deleteDirectory(String dirName) throws Exception;

    void addFileToDirectory();

    void moveFile();

    void removeFileFromDirectory();

    List<FileEntity> getAllFilesFromDirectory(String dirName);

    List getListOfDirectories() throws Exception;

    DirectoryEntity getDirectoryEntityByName(String dirName);

    DirectoryEntity getDirectoryEntityById(Long id);

}
