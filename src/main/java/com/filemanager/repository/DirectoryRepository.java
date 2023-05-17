package com.filemanager.repository;

import com.filemanager.model.DirectoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DirectoryRepository extends JpaRepository<DirectoryEntity,Integer> {

    public DirectoryEntity findByDirectoryName(String dirName);

    public void deleteByDirectoryName(String dirName);

    public DirectoryEntity findByDirectoryId(Integer id);
}
