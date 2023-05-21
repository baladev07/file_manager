package com.filemanager.dto;


import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import com.filemanager.Util.DirectoryUtils;
import com.filemanager.appconfig.EntityContextHolder;
import com.filemanager.dao.DirectoryDAO;
import com.filemanager.impl.UserServiceImpl;
import com.filemanager.model.DirectoryEntity;
import com.filemanager.model.UserEntity;
import com.filemanager.repository.DirectoryRepository;
import com.filemanager.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

@Data
@AllArgsConstructor
@JsonDeserialize(using = DirectoryRequestDTO.DirectoryRequestDTODeserializer.class)
public class DirectoryRequestDTO {

    private String dirName;

    private String parentDirName;

    private DirectoryEntity parentEntity;

    public String getDirName()
    {
        return dirName;
    }

    private String dirPath;

    private String currDirPath;


    public static class DirectoryRequestDTODeserializer extends JsonDeserializer<DirectoryRequestDTO> {
        @Autowired
        DirectoryRepository directoryRepository;

        @Autowired
        DirectoryDAO directoryDAO;

        @Override
        public DirectoryRequestDTO deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            ObjectCodec oc = p.getCodec();
            JsonNode node = oc.readTree(p);
            String parentDirName = "";
            String dirName = "";
            if(node.get("parent_dir_name")!=null)
            {
                parentDirName = node.get("parent_dir_name").asText();
            }
            if(node.get("dir_name")!=null)
            {
                dirName = node.get("dir_name").asText();
            }
            DirectoryEntity parentEntity = null;
            if (parentDirName == null || parentDirName.isEmpty()) {

                if (EntityContextHolder.getEntityContext().getEntity(UserEntity.class) != null) {
                    UserEntity userEntity = EntityContextHolder.getEntityContext().getEntity(UserEntity.class);
                    String userDirName = userEntity.getUserDirName();
                    parentEntity = directoryRepository.findByDirectoryName(userDirName);
                }
                else {
                    parentEntity =  directoryRepository.findByDirectoryName(DirectoryUtils.rootPathName);
                }
            }
            else {
                if (EntityContextHolder.getEntityContext().getEntity(UserEntity.class) != null)
                {
                    UserEntity userEntity = EntityContextHolder.getEntityContext().getEntity(UserEntity.class);
                    Long directoryId = userEntity.getDirectoryEntity().getDirectoryId();
                    List<DirectoryEntity> subDirectories = directoryRepository.getSubDirectoriesList(directoryId);
                    if(!subDirectories.isEmpty())
                    {
                        for(DirectoryEntity directory:subDirectories)
                        {
                            if(directory.getDirectoryName().equals(parentDirName))
                            {
                                parentEntity = directory;
                                break;
                            }
                        }
                    }
                }
                else {
                    parentEntity =  directoryRepository.findByDirectoryName(parentDirName);
                }
            }
            String abs =  parentEntity.getDirectoryPath();
            String currDirPath = parentEntity.getDirectoryPath()+"/"+dirName;
            return new DirectoryRequestDTO(dirName, parentDirName, parentEntity,abs,currDirPath);
        }
    }
}
