package com.filemanager.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileResponseDTO implements ResponseDTO{
    @JsonProperty("file_id")
    private long fileId;
    @JsonProperty("file_name")
    private String fileName;
    @JsonProperty("file_uploaded_time")
    private long fileUploadedTime;
    @JsonProperty("file_format")
    private String fileFormat;

}
