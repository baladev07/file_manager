package com.filemanager.appconfig;

import com.filemanager.file.FileManager;
import com.filemanager.file.PathConstructor;
import com.filemanager.model.DirectoryEntity;
import com.filemanager.model.FileEntity;
import com.filemanager.repository.DirectoryRepository;
import com.filemanager.repository.FileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class FileMangerInitializer implements ApplicationListener<ContextRefreshedEvent> {


    Logger logger = LoggerFactory.getLogger(FileMangerInitializer.class);

    @Autowired
    DirectoryRepository directoryRepository;

    @Autowired
    FileRepository fileRepository;

    private static boolean isPopulated = false;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try{
            if (!isPopulated && !directoryRepository.findAll().iterator().hasNext()) {
                createRootDirectory();
                isPopulated = true;
            }
        }
        catch(Exception ex)
        {
            logger.error("Exception occurred during data initializing",ex);
        }
    }

    public void createSampleFile(String fileName,String format,DirectoryEntity directoryEntity)
    {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(fileName);
        fileEntity.setDirectoryEntity(directoryEntity);
        fileEntity.setFileFormat(format);
        fileRepository.save(fileEntity);
    }

    public  void createRootDirectory() throws Exception {
        DirectoryEntity directoryEntity = new DirectoryEntity();
        directoryEntity.setDirectoryName("root");
        directoryEntity.setParentDirectory(null);
        String rootPath = PathConstructor.CURR_PATH + "/root";
        directoryEntity.setDirectoryPath("/root");
        FileManager.createDirectory(rootPath);
        directoryRepository.save(directoryEntity);
    }
}
