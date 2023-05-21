package com.filemanager.file;

import com.filemanager.Util.DirectoryUtils;
import com.filemanager.Util.ErrorMessages;
import com.filemanager.Util.SuccessMessageConstants;
import com.filemanager.dto.FileDetailsDTO;

import com.filemanager.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    // use this method to delete file
    public static void deleteFile(String path) {
        File dir = new File(path);
        if (dir.delete()) {
            logger.info(path + " " + SuccessMessageConstants.FILE_DELETED);
            return;
        }
        logger.error(ErrorMessages.FILE_NOT_EXISTS);
        throw new BadRequestException(ErrorMessages.FILE_NOT_EXISTS);
    }

    public static void deleteDirectory(String dirPath,boolean isEmptyDir)
    {
        if (isEmptyDir)
        {
            logger.info(dirPath + " directory is empty.");
            File dir = new File(dirPath);
            if (dir.delete()) {
                logger.info(dirPath +" "+SuccessMessageConstants.DIR_DELETED);
            }
            else {
                logger.info(ErrorMessages.DIRECTORY_NOT_EXISTS);
                throw new BadRequestException(ErrorMessages.DIRECTORY_NOT_EXISTS);
            }
        }
        else {
            deleteDirectoryAndFiles(dirPath);
        }
    }

    public static void uploadFile(FileDetailsDTO fileDetailsObject, String path) throws Exception {
        try{
            byte[] fileBytes =  fileDetailsObject.getBytes();
            String filePath  = DirectoryUtils.CURR_PATH+path+"/"+fileDetailsObject.getFileNameWithFormat();
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                fos.write(fileBytes);
            }
            logger.info("File successfully stored.");
        }
        catch(Exception e)
        {
            logger.error("Exception occurred during storing the file",e);
            throw new BadRequestException("Something went wrong. please try again");
        }
    }

    public static void deleteDirectoryAndFiles(String dirPath)
    {
        try {
            Path directory = Paths.get(dirPath);
            // Deleting the directory and its contents recursively
            Files.walk(directory)
                    .sorted((p1, p2) -> -p1.compareTo(p2))
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            logger.error("Failed to delete directory: "+ dirPath,e);
                        }
                    });
            logger.info("Directory deleted successfully.");
        } catch (IOException e) {
            logger.error("Failed to delete directory: "+ dirPath,e);
        }
    }

    public static byte[] downloadFile(String filePath) {
        byte[] fileBytes = null;
        try {
            fileBytes = Files.readAllBytes(Path.of(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileBytes;
    }

}
