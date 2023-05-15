package com.filemanager.impl;

import com.filemanager.Util.DirectoryUtils;
import com.filemanager.dto.FileDetailsObject;
import com.filemanager.repository.FileRepository;
import com.filemanager.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;


@Service
public class FileServiceImpl implements FileService {

    Logger logger = LoggerFactory.getLogger(DirectoryServiceImpl.class);

    @Autowired
    FileRepository fileRepository;



    @Override
    public void store(FileDetailsObject fileDetailsObject) throws Exception {
        try{
            byte[] fileBytes =  fileDetailsObject.getBytes();
            String filePath  = DirectoryUtils.getAbsolutePath("root/")+fileDetailsObject.getOriginalFileName();
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                fos.write(fileBytes);
            }
            logger.info("File successfully stored.");
            fileRepository.save(fileDetailsObject.getFileEntity());
        }
        catch(Exception e)
        {
            throw new Exception("Exception occurred during storing the file",e);
        }
    }

    @Override
    public void delete() {

    }

    @Override
    public void getFile() {

    }
}
