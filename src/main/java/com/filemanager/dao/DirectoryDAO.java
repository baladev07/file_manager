package com.filemanager.dao;

import com.filemanager.appconfig.FileMangerInitializer;
import com.filemanager.model.DirectoryEntity;
import com.filemanager.model.FileEntity;
import com.filemanager.repository.DirectoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;


@Repository
public class DirectoryDAO {

    Logger logger = LoggerFactory.getLogger(DirectoryDAO.class);

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

    public DirectoryEntity getParentEntity(String path)
    {
        DirectoryEntity directoryEntity = null;
        try{
            if(!path.isEmpty())
            {
                String[] directories = path.split("/");
                String parentDirName = directories[directories.length-1];
                directoryEntity = directoryRepository.findByDirectoryName(parentDirName);
            }
        }

        catch(Exception e)
        {
            logger.error("Exception occurred during in Directory DAO:",e);
            throw e;
        }
        return directoryEntity;
    }

    public List<DirectoryEntity> getSubDirectories(Long directoryId)
    {
        DirectoryEntity directoryEntity = directoryRepository.findByDirectoryId(directoryId);
        DirectoryEntity parentEntity = directoryEntity.getParentDirectory();
        List<DirectoryEntity>directoryEntityList = new ArrayList<>();
        while(parentEntity!=null)
        {
            directoryEntityList.add(parentEntity);
            parentEntity = parentEntity.getParentDirectory();
        }
        return directoryEntityList;
    }




}
