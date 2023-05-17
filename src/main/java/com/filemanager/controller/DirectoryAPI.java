package com.filemanager.controller;


import com.filemanager.Util.SuccessMessageConstants;
import com.filemanager.dao.DirectoryDAO;
import com.filemanager.dto.DirectoryRequestDTO;
import com.filemanager.dto.DirectoryResponseDTO;
import com.filemanager.dto.EntityResponseBuilder;
import com.filemanager.exception.BadRequestException;
import com.filemanager.http.HttpResponseBuilder;
import com.filemanager.model.DirectoryEntity;
import com.filemanager.service.DirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;


@RestController
@Validated
@RequestMapping("/api")
public class DirectoryAPI
{
    @Autowired
    DirectoryService directoryService;
    @Autowired HttpResponseBuilder httpResponseBuilder;

    @Autowired EntityResponseBuilder entityResponseBuilder;


    @Autowired
    DirectoryDAO directoryDAO;

    @PostMapping("/directory")
    public ResponseEntity createDirectory(@RequestBody DirectoryRequestDTO directoryRequest) throws Exception {
        directoryService.createDirectory(directoryRequest);
        return ResponseEntity.ok(httpResponseBuilder.message(SuccessMessageConstants.DIR_CREATED).build());
    }


    @GetMapping("/directories")
    public ResponseEntity listAllDirectories() throws Exception {
       return ResponseEntity.ok(httpResponseBuilder.message("success").data("directories",directoryService.getListOfDirectories()).build());
    }

    @GetMapping("/directory/{id}")
    public ResponseEntity listAllDirectories(@PathVariable @Pattern(regexp = "\\d+", message = "Invalid ID")String id)
    {
        DirectoryEntity directoryEntity = directoryService.getDirectoryEntityById(Integer.valueOf(id));
        if(directoryEntity==null)
        {
            throw new BadRequestException("No directory found");
        }
        return ResponseEntity.ok(httpResponseBuilder.message("success").data("directory",entityResponseBuilder.getResponseDTO()).build());
    }

    @DeleteMapping("/directory/{dirName}")
    public ResponseEntity deleteDirectory(@PathVariable String dirName) throws Exception {
        directoryService.deleteDirectory(dirName);
        return ResponseEntity.ok(httpResponseBuilder.message(SuccessMessageConstants.DIR_DELETED).build());
    }

}
