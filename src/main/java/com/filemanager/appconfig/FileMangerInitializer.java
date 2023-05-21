package com.filemanager.appconfig;

import com.filemanager.Util.DirectoryUtils;
import com.filemanager.file.FileManager;
import com.filemanager.file.PathConstructor;
import com.filemanager.model.DirectoryEntity;
import com.filemanager.model.FileEntity;
import com.filemanager.model.UserEntity;
import com.filemanager.repository.DirectoryRepository;
import com.filemanager.repository.FileRepository;
import com.filemanager.repository.UserRepository;
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

    @Autowired
    UserRepository userRepository;

    private static boolean isPopulated = false;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try{
            if (!isPopulated && !directoryRepository.findAll().iterator().hasNext()) {
                createRootDirectory();
                createUserIfNotExist("user2","user2@gmail.com","user2");
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
        directoryEntity.setDirectoryName(DirectoryUtils.rootPathName);
        directoryEntity.setParentDirectory(null);
        String rootPath = DirectoryUtils.CURR_PATH + "/"+DirectoryUtils.rootPathName;
        directoryEntity.setDirectoryPath("/"+DirectoryUtils.rootPathName);
        FileManager.createDirectory(rootPath);
        directoryRepository.save(directoryEntity);
    }

    public void createUserIfNotExist(String name, String email, String password)
    {
        UserEntity userEntity = userRepository.findByUsername(name);
        if(userEntity ==null)
        {
            userEntity = new UserEntity();
            userEntity.setUsername(name);
            userEntity.setEmail(email);
            userEntity.setPassword(password);
            userRepository.save(userEntity);
        }
    }
}
