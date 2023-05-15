package com.filemanager.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DirectoryResponseDTO {


    @JsonProperty("directory_id")
    private int directoryId;


    @JsonProperty("directory_name")
    private String directoryName;


    @JsonProperty("directory_path")
    private String directoryPath;



    @JsonProperty(value="parent_dir_id",defaultValue = "null")
    private int parentDirId;
}
