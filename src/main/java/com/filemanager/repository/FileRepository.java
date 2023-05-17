package com.filemanager.repository;

import com.filemanager.model.DirectoryEntity;
import com.filemanager.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FileRepository extends JpaRepository<FileEntity, Integer> {

    public FileEntity findByFileId(long id);

    List<FileEntity> findByDirectoryEntity(DirectoryEntity directoryEntity);

}
