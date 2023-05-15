package com.filemanager.controller;


import com.filemanager.Util.SuccessMessageConstants;
import com.filemanager.dao.DirectoryDAO;
import com.filemanager.dto.DirectoryRequestDTO;
import com.filemanager.http.HttpResponseBuilder;
import com.filemanager.service.DirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DirectoryAPI
{
    @Autowired
    DirectoryService directoryService;


    @Autowired
    DirectoryDAO directoryDAO;

    @PostMapping("/directory")
    public ResponseEntity createDirectory(@RequestBody DirectoryRequestDTO directoryRequest)  {
        directoryService.createDirectory(directoryRequest);
        return ResponseEntity.ok(new HttpResponseBuilder().message(SuccessMessageConstants.DIR_CREATED).build());
    }


    @GetMapping("/directories")
    public ResponseEntity listAllDirectories()
    {
       return ResponseEntity.ok(new HttpResponseBuilder().message("success").data("directories",directoryService.getListOfDirectories()).build());
    }

    @DeleteMapping("/directory/{dirName}")
    public ResponseEntity deleteDirectory(@PathVariable String dirName)
    {
        directoryService.deleteDirectory(dirName);
        return ResponseEntity.ok(new HttpResponseBuilder().message(SuccessMessageConstants.DIR_DELETED).build());
    }

}
