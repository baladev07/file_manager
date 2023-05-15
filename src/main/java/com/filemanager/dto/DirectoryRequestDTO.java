package com.filemanager.dto;


import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import com.filemanager.Util.DirectoryUtils;
import com.filemanager.model.DirectoryEntity;
import com.filemanager.repository.DirectoryRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

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

    private String absoluteDirPath;

    private String currDirPath;


    public static class DirectoryRequestDTODeserializer extends JsonDeserializer<DirectoryRequestDTO> {
        @Autowired
        DirectoryRepository directoryRepository;

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
                parentEntity =  directoryRepository.findByDirectoryName("root");

            }
            else {
                parentEntity =  directoryRepository.findByDirectoryName(parentDirName);
            }
            String abs =DirectoryUtils.CURR_PATH+parentEntity.getDirectoryPath()+"/"+dirName;
            String currDirPath = parentEntity.getDirectoryPath()+"/"+dirName;
            return new DirectoryRequestDTO(dirName, parentDirName, parentEntity,abs,currDirPath);
        }
    }
}
