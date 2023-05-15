package com.filemanager.impl;

import com.filemanager.Util.DirectoryUtils;
import com.filemanager.Util.ErrorMessages;
import com.filemanager.dao.DirectoryDAO;
import com.filemanager.dto.DirectoryRequestDTO;
import com.filemanager.dto.DirectoryResponseDTO;
import com.filemanager.exception.BadRequestException;
import com.filemanager.file.FileManager;
import com.filemanager.file.PathConstructor;
import com.filemanager.model.DirectoryEntity;
import com.filemanager.model.FileEntity;
import com.filemanager.repository.DirectoryRepository;
import com.filemanager.service.DirectoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class DirectoryServiceImpl implements DirectoryService {

    Logger logger = LoggerFactory.getLogger(DirectoryServiceImpl.class);
    @Autowired
    DirectoryRepository directoryRepository;

    @Autowired
    DirectoryDAO directoryDAO;

//    @Autowired
//    PathConstructor pathConstructor;

    @Override
    @Transactional
    public void createDirectory(DirectoryRequestDTO directoryRequestDTO) {
        DirectoryEntity directoryEntity = new DirectoryEntity();
        String abs = directoryRequestDTO.getAbsoluteDirPath();
        directoryEntity.setParentDirectory(directoryRequestDTO.getParentEntity());
        directoryEntity.setDirectoryName(directoryRequestDTO.getDirName());
        directoryEntity.setDirectoryPath(directoryRequestDTO.getCurrDirPath());
        directoryRepository.save(directoryEntity);
        FileManager.createDirectory(abs);
        logger.info("Directory created and saved successfully.");
    }

    @Override
    @Transactional
    public void deleteDirectory(String dirName) {
        DirectoryEntity directoryEntity = getDirectory(dirName);
        if (dirName.equals(DirectoryUtils.root)) {
            throw new BadRequestException(ErrorMessages.ROOT_NOT_DELETED);
        } else if (directoryEntity != null) {
            directoryRepository.deleteByDirectoryName(dirName);
            FileManager.deleteDirectory(DirectoryUtils.CURR_PATH+"/"+directoryEntity.getDirectoryPath(), isEmptyDir(dirName));
        } else {
            logger.info("Directory not exists.");
            throw new BadRequestException(ErrorMessages.DIRECTORY_NOT_EXISTS);
        }
    }

    @Override
    public void addFileToDirectory() {

    }

    @Override
    public void moveFile() {

    }

    @Override
    public void removeFileFromDirectory() {

    }

    @Override
    public List<FileEntity> getAllFilesFromDirectory(String name) {
        return directoryDAO.getAllFilesFromDirectory(name);
    }

    @Override
    public List getListOfDirectories() {

        List<DirectoryEntity>directoryEntityList = directoryRepository.findAll();
        List<DirectoryResponseDTO> responseDTOS = new ArrayList<>();
        for(DirectoryEntity directory:directoryEntityList)
        {
            DirectoryResponseDTO responseDTO = new DirectoryResponseDTO();
            responseDTO.setDirectoryId(directory.getDirectoryId());
            responseDTO.setDirectoryPath(directory.getDirectoryPath());
            if(directory.getParentDirectory()!=null)
            {
                responseDTO.setParentDirId(directory.getParentDirectory().getDirectoryId());
            }
            responseDTO.setDirectoryName(directory.getDirectoryName());
            responseDTOS.add(responseDTO);
        }
        return responseDTOS;
    }


    @Override
    public DirectoryEntity getDirectory(String name)
    {
        return directoryRepository.findByDirectoryName(name);
    }

    public boolean isEmptyDir(String name)
    {
        return getAllFilesFromDirectory(name).isEmpty();
    }

}
