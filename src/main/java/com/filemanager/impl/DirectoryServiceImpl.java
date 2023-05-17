package com.filemanager.impl;

import com.filemanager.Util.DirectoryUtils;
import com.filemanager.Util.ErrorMessages;
import com.filemanager.dao.DirectoryDAO;
import com.filemanager.dto.DirectoryRequestDTO;
import com.filemanager.dto.DirectoryResponseDTO;
import com.filemanager.dto.EntityResponseBuilder;
import com.filemanager.exception.BadRequestException;
import com.filemanager.file.FileManager;
import com.filemanager.model.DirectoryEntity;
import com.filemanager.model.FileEntity;
import com.filemanager.repository.DirectoryRepository;
import com.filemanager.service.DirectoryService;
import com.filemanager.service.FileService;
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

    @Autowired
    EntityResponseBuilder entityResponseBuilder;

    @Autowired
    FileService fileService;



    @Override
    @Transactional
    public void createDirectory(DirectoryRequestDTO directoryRequestDTO) throws Exception {
        try{
            DirectoryEntity directoryEntity = new DirectoryEntity();
            String abs = directoryRequestDTO.getAbsoluteDirPath();
            directoryEntity.setParentDirectory(directoryRequestDTO.getParentEntity());
            directoryEntity.setDirectoryName(directoryRequestDTO.getDirName());
            directoryEntity.setDirectoryPath(directoryRequestDTO.getCurrDirPath());
            directoryRepository.save(directoryEntity);
            FileManager.createDirectory(abs);
            logger.info("Directory created and saved successfully.");
        }
        catch (Exception e)
        {
            logger.error("Exception occurred during creating directory.",e);
            throw new Exception();
        }
    }

    @Override
    @Transactional
    public void deleteDirectory(String dirName) throws Exception {
        try
        {
            DirectoryEntity directoryEntity = getDirectoryEntityByName(dirName);
            if (dirName.equals(DirectoryUtils.root)) {
                throw new BadRequestException(ErrorMessages.ROOT_NOT_DELETED);
            } else if (directoryEntity != null) {
                directoryRepository.deleteByDirectoryName(dirName);
                List<FileEntity> files = fileService.getFilesListInDirectory(directoryEntity);
                for(FileEntity file:files)
                {
                    fileService.delete(file.getFileId());
                }
                FileManager.deleteDirectory(DirectoryUtils.CURR_PATH+"/"+directoryEntity.getDirectoryPath(), isEmptyDir(dirName));
            } else {
                logger.info("Directory not exists.");
                throw new BadRequestException(ErrorMessages.DIRECTORY_NOT_EXISTS);
            }
        }
        catch(BadRequestException ex)
        {
            throw ex;
        }
        catch(Exception ex )
        {
            logger.info("Exception occurred while deleting directory.");
            throw new Exception();
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
    public List getListOfDirectories() throws Exception {
        try
        {
            List<DirectoryEntity>directoryEntityList = directoryRepository.findAll();
            List<DirectoryResponseDTO> responseDTOS = new ArrayList<>();
            for(DirectoryEntity directory:directoryEntityList)
            {
                responseDTOS.add(entityResponseBuilder.buildResponseDTO(directory));
            }
            return responseDTOS;
        }
        catch(Exception ex)
        {
            logger.info("Exception occurred while getting list of directories:",ex);
            throw new Exception();
        }
    }


    @Override
    public DirectoryEntity getDirectoryEntityByName(String name)
    {
        return directoryRepository.findByDirectoryName(name);
    }

    public boolean isEmptyDir(String name)
    {
        return getAllFilesFromDirectory(name).isEmpty();
    }

    public DirectoryEntity getDirectoryEntityById(Integer id)
    {
        DirectoryEntity directoryEntity = directoryRepository.findByDirectoryId(id);
        entityResponseBuilder.buildResponseDTO(directoryEntity);
        return directoryEntity;

    }

}
