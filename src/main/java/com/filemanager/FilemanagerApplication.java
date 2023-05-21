package com.filemanager;

import com.filemanager.model.DirectoryEntity;
import com.filemanager.model.FileEntity;
import com.filemanager.repository.DirectoryRepository;
import com.filemanager.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication

public class FilemanagerApplication {


	@Autowired
	DirectoryRepository directoryRepository;

	@Autowired
	FileRepository fileRepository;

	public static void main(String[] args) {
		SpringApplication.run(FilemanagerApplication.class, args);
	}

}
