package com.filemanager.service;

import com.filemanager.dto.DirectoryRequestDTO;
import com.filemanager.model.DirectoryEntity;
import com.filemanager.model.FileEntity;

import java.util.List;

public interface DirectoryService{


    void createDirectory(DirectoryRequestDTO directoryRequestDTO);

    void deleteDirectory(String dirName);

    void addFileToDirectory();

    void moveFile();

    void removeFileFromDirectory();

    List<FileEntity> getAllFilesFromDirectory(String dirName);

    List getListOfDirectories();

    DirectoryEntity getDirectory(String dirName);

}
