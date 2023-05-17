package com.filemanager.dto;

import com.filemanager.model.DirectoryEntity;
import com.filemanager.model.FileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;


@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class EntityResponseBuilder {

    private ResponseDTO responseDTO;

    public DirectoryResponseDTO buildResponseDTO(DirectoryEntity directoryEntity)
    {
        Long parenDirId = null;
        if(directoryEntity.getParentDirectory()!=null)
        {
            parenDirId = directoryEntity.getParentDirectory().getDirectoryId();
        }
        DirectoryResponseDTO directoryResponseDTO = DirectoryResponseDTO.builder().
                directoryId(directoryEntity.getDirectoryId()).directoryName(directoryEntity.getDirectoryName())
                        .directoryPath(directoryEntity.getDirectoryPath())
                                .parentDirId(parenDirId).build();

        this.responseDTO = directoryResponseDTO;
        return directoryResponseDTO;
    }

    public FileResponseDTO buildResponseDTO(FileEntity fileEntity)
    {
        FileResponseDTO fileResponseDTO = FileResponseDTO.builder()
                .fileFormat(fileEntity.getFileFormat())
                .fileName(fileEntity.getFileName()).
                fileUploadedTime(fileEntity.getFileUploadedTime())
                .fileId(fileEntity.getFileId())
                .build();
         this.responseDTO = fileResponseDTO;
         return fileResponseDTO;
    }

    public ResponseDTO getResponseDTO()
    {
        return this.responseDTO;
    }

}
