package com.filemanager.file;

import com.filemanager.Util.DirectoryUtils;
import com.filemanager.Util.ErrorMessages;
import com.filemanager.Util.SuccessMessageConstants;
import com.filemanager.appconfig.FileMangerInitializer;
import com.filemanager.exception.BadRequestException;
import com.filemanager.exception.DirectoryNotCreatedException;
import com.filemanager.model.DirectoryEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;

public class FileManager {


    static Logger logger = LoggerFactory.getLogger(FileManager.class);

    public static void createDirectory(String dirPath){
        File directory = new File(dirPath);
        if (!directory.exists()) {
            boolean created = directory.mkdir();
            if (created) {
                logger.info(dirPath+" Directory created successfully.");
            } else {
                logger.info("Incorrect parent directory.");
                throw new BadRequestException(ErrorMessages.DIR_CREATION_FAILED);
            }
        } else {
            logger.info("Directory already exists.");
            throw new BadRequestException(ErrorMessages.DIR_ALREADY_EXISTS);
        }
    }

    public static void deleteDirectory(String dirPath,boolean isEmptyDir)
    {
        if (isEmptyDir) {
            logger.info(dirPath + " directory is empty.");
            File dir = new File(dirPath);
            if (dir.delete()) {
                logger.info(dirPath +" "+SuccessMessageConstants.DIR_DELETED);
            }
        } else {
            logger.info(ErrorMessages.DIRECTORY_NOT_EXISTS);
            throw new BadRequestException(ErrorMessages.DIRECTORY_NOT_EXISTS);
        }
    }

    public static String constructAbsolutePath(DirectoryEntity directoryEntity)
    {
        DirectoryEntity root = null;
        StringBuilder pathBuilder = new StringBuilder();
        while(directoryEntity!=null)
        {
            DirectoryEntity currEntity = directoryEntity;
            DirectoryEntity parentEntity = directoryEntity.getParentDirectory();
            currEntity.setParentDirectory(root);
            root = directoryEntity;
            directoryEntity = parentEntity;
        }
        while(root!=null)
        {
            pathBuilder.append(root.getDirectoryName()+"/");
            root = root.getParentDirectory();
        }

        return pathBuilder.toString();

    }

}
