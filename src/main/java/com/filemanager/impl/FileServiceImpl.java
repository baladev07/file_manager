package com.filemanager.impl;

import com.filemanager.Util.DirectoryUtils;
import com.filemanager.Util.ErrorMessages;
import com.filemanager.appconfig.EntityContextHolder;
import com.filemanager.custom_annotations.UniqueConstraintException;
import com.filemanager.dto.EntityResponseBuilder;
import com.filemanager.dto.FileDetailsDTO;
import com.filemanager.dto.FileResponseDTO;
import com.filemanager.dto.ResponseDTO;
import com.filemanager.exception.BadRequestException;
import com.filemanager.file.FileManager;
import com.filemanager.model.DirectoryEntity;
import com.filemanager.model.FileEntity;
import com.filemanager.model.UserEntity;
import com.filemanager.repository.DirectoryRepository;
import com.filemanager.repository.FileRepository;
import com.filemanager.service.DirectoryService;
import com.filemanager.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class FileServiceImpl implements FileService {

    Logger logger = LoggerFactory.getLogger(DirectoryServiceImpl.class);

    @Autowired
    FileRepository fileRepository;

    @Autowired
    DirectoryRepository directoryRepository;

    @Autowired
    EntityResponseBuilder entityResponseBuilder;


    @Override
    @UniqueConstraintException
    public void upload(FileDetailsDTO fileDetailsObject) throws Exception {
        try{

            UserEntity userEntity = EntityContextHolder.getEntityContext().getEntity(UserEntity.class);
            FileEntity entity = new FileEntity();
            DirectoryEntity directoryEntity = directoryRepository.findByDirectoryName(fileDetailsObject.getDirName());
            if(directoryEntity==null)
            {
                throw new BadRequestException(ErrorMessages.DIRECTORY_NOT_EXISTS);
            }
            entity.setDirectoryEntity(directoryEntity);
            entity.setFileFormat(fileDetailsObject.getFileFormat());
            entity.setFileSize(fileDetailsObject.getSize());
            entity.setFileName(fileDetailsObject.getFileName());
            entity.setFileUploadedTime(new Date().getTime());
            entity.setUserEntity(userEntity);
            fileRepository.save(entity);
            FileManager.uploadFile(fileDetailsObject,directoryEntity.getDirectoryPath());
            logger.info("File successfully uploaded and saved.");
            entityResponseBuilder.buildResponseDTO(entity);
        }
        catch(Exception ex)
        {
            logger.error("Exception occurred during uploading file.",ex);
            throw ex;
        }

    }


    @Override
    @Transactional
    public void delete(long id) throws Exception {
        try{
            FileEntity fileEntity = getFile(id);
            if(fileEntity!=null)
            {
                String filePath = fileEntity.getFilePath();
                FileManager.deleteFile(filePath);
                fileRepository.delete(fileEntity);
                return;
            }
            logger.error("No file entity found for this id.");
            throw new BadRequestException(ErrorMessages.FILE_NOT_EXISTS);
        }
        catch(BadRequestException ex)
        {
            throw ex;
        }
        catch(Exception ex)
        {
            logger.info("Exception occurred during deleting the file.",ex);
            throw new Exception();
        }

    }

    @Override
    public FileEntity getFile(long id) {
        FileEntity fileEntity = fileRepository.findByFileId(id);
        if(fileEntity!=null)
        {
            entityResponseBuilder.buildResponseDTO(fileEntity);
            return fileEntity;
        }
        logger.error("No file entity found for this id.");
        throw new BadRequestException(ErrorMessages.FILE_NOT_EXISTS);
    }

    public List getFilesList()
    {
        UserEntity userEntity = EntityContextHolder.getEntityContext().getEntity(UserEntity.class);
        List<FileEntity>fileEntities = fileRepository.findByUserEntity(userEntity);
        List<ResponseDTO>fileDetailsDTOS = new ArrayList<>();
        for(FileEntity fileEntity:fileEntities)
        {
            FileResponseDTO responseDTO = entityResponseBuilder.buildResponseDTO(fileEntity);
            fileDetailsDTOS.add(responseDTO);
        }
        return fileDetailsDTOS;
    }

    public List getFilesListInDirectory(DirectoryEntity directoryEntity)
    {
        return fileRepository.findByDirectoryEntity(directoryEntity);
    }

    @Override
    public void downloadFile(long id) {
        FileEntity entity = getFile(id);
        entity.getFilePath();
    }
}
