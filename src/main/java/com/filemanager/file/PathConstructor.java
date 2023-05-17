package com.filemanager.file;


import com.filemanager.Util.DirectoryUtils;
import com.filemanager.Util.ErrorMessages;
import com.filemanager.dto.DirectoryRequestDTO;
import com.filemanager.exception.BadRequestException;
import com.filemanager.model.DirectoryEntity;
import com.filemanager.service.DirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;



@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PathConstructor {

    public static String CURR_PATH = System.getProperty("user.dir");

    public static String ABSOLUTE_ROOT_DIR_PATH = System.getProperty("user.dir")+"/"+ DirectoryUtils.rootPathName;

    public static String ROOT_PATH = "/"+DirectoryUtils.rootPathName;

    private DirectoryRequestDTO directoryRequestDTO;

    @Autowired
    DirectoryService directoryService;

    private String currDirName;

    private String parentDirName;

    private String parentDirPath;

    private String currDirPath;



    public void set(DirectoryRequestDTO directoryRequestDTO)
    {
        this.directoryRequestDTO = directoryRequestDTO;
        this.currDirName = directoryRequestDTO.getDirName();
        this.parentDirName = directoryRequestDTO.getParentEntity().getDirectoryName();

    }

    public void set(String currDir)
    {
        this.currDirName = currDir;
        DirectoryEntity directoryEntity = directoryService.getDirectoryEntityByName(currDir);
        this.parentDirName = directoryEntity.getParentDirectory().getDirectoryName();
    }

    public String getPath()
    {
        this.currDirPath = ROOT_PATH+"/"+currDirName;
        return ABSOLUTE_ROOT_DIR_PATH+"/"+currDirName;
    }

    private void constructAbsolutePathWithParentDir(DirectoryEntity directoryEntity)
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
        this.currDirPath = "/"+pathBuilder+currDirName;
        this.parentDirPath = CURR_PATH+"/"+pathBuilder+currDirName;
    }

    private String getParentDirPath()
    {
        DirectoryEntity parentDirEntity = null;
        if (!parentDirName.isEmpty()) {
            parentDirEntity = directoryService.getDirectoryEntityByName(this.parentDirName);
            if (parentDirEntity != null) {
                constructAbsolutePathWithParentDir(parentDirEntity);
            } else {
                throw new BadRequestException(ErrorMessages.INVALID_PARENT_DIR);
            }
        }
        return this.parentDirPath;
    }

    public String getAbsolutePath()
    {
        if(parentDirName.isEmpty())
        {
            return getPath();
        }
        return getParentDirPath();
    }

    public String getCurrDirPath()
    {
        if(!this.currDirPath.isEmpty())
        {
            return this.currDirPath;
        }
        return "";
    }
}
