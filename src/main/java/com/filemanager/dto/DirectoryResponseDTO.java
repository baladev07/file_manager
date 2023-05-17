package com.filemanager.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.filemanager.model.DirectoryEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Data
@Builder
public class DirectoryResponseDTO implements ResponseDTO {


    @JsonProperty("directory_id")
    private long directoryId;


    @JsonProperty("directory_name")
    private String directoryName;


    @JsonProperty("directory_path")
    private String directoryPath;


    @JsonProperty(value="parent_dir_id",defaultValue = "null")
    private long parentDirId;

}
