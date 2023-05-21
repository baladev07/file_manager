package com.filemanager.repository;

import com.filemanager.model.DirectoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DirectoryRepository extends JpaRepository<DirectoryEntity,Integer> {


    public DirectoryEntity findByDirectoryName(String dirName);


    @Query("SELECT d FROM DirectoryEntity d WHERE d.directoryPath LIKE CONCAT('%', (SELECT d2.directoryPath FROM DirectoryEntity d2 WHERE d2.directoryId = :parentId), '%') AND d.directoryName = :dirName")
    public DirectoryEntity findByDirectoryName(@Param("dirName") String dirName,@Param("parentId")Long parentId);


    public void deleteByDirectoryName(String dirName);

    public DirectoryEntity findByDirectoryId(Long id);



    @Query("SELECT d FROM DirectoryEntity d WHERE d.directoryPath LIKE CONCAT('%', (SELECT d2.directoryPath FROM DirectoryEntity d2 WHERE d2.directoryId = :parentId), '%')")
    List<DirectoryEntity> getSubDirectoriesList(@Param("parentId") Long parentId);
}
