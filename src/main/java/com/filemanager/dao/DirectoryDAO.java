package com.filemanager.dao;

import com.filemanager.model.DirectoryEntity;
import com.filemanager.model.FileEntity;
import com.filemanager.repository.DirectoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.*;
import java.util.List;


@Repository
public class DirectoryDAO {

    @Autowired
    EntityManager entityManager;

    @Autowired
    DirectoryRepository directoryRepository;

    public List<FileEntity>getAllFilesFromDirectory(String dirName)
    {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<FileEntity> query = cb.createQuery(FileEntity.class);
        Root<FileEntity> fileEntityRoot = query.from(FileEntity.class);
        Join<FileEntity, DirectoryEntity> fileEntityJoinJoin = fileEntityRoot.join("directoryEntity");
        Predicate condition = cb.equal(fileEntityJoinJoin.get("directoryName"), dirName);
        query.where(condition);
        List<FileEntity> files = entityManager.createQuery(query).getResultList();
        return files;
    }

    public DirectoryEntity getListOfParentDirectories(String dirName)
    {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DirectoryEntity> criteriaQuery = criteriaBuilder.createQuery(DirectoryEntity.class);
        Root<DirectoryEntity> directoryRoot = criteriaQuery.from(DirectoryEntity.class);

        Join<DirectoryEntity, DirectoryEntity> parentJoin = directoryRoot.join("parentDirectory", JoinType.LEFT);

        criteriaQuery.select(parentJoin);
        criteriaQuery.where(criteriaBuilder.equal(directoryRoot.get("directoryName"), dirName));

        DirectoryEntity parentDirectories = entityManager.createQuery(criteriaQuery).getSingleResult();

        return parentDirectories;

    }



}
