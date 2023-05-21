package com.filemanager.impl;

import com.filemanager.Util.DirectoryUtils;
import com.filemanager.custom_annotations.UniqueConstraintException;
import com.filemanager.dto.SignUpRequestDTO;
import com.filemanager.exception.BadRequestException;
import com.filemanager.model.DirectoryEntity;
import com.filemanager.model.UserEntity;
import com.filemanager.repository.UserRepository;
import com.filemanager.service.DirectoryService;
import com.filemanager.service.UserService;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {


    static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    DirectoryService directoryService;


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
    public void addUser(UserEntity userEntity) throws Exception {

        if(userEntity.getUsername().length()<4)
        {
            throw new BadRequestException("username must contains minimum 4 characters.");
        }
        userRepository.save(userEntity);
        String userDirName = userEntity.getUserDirName();
        DirectoryEntity directoryEntity = directoryService.createDirectory(userDirName, DirectoryUtils.rootPth);
        userEntity.setDirectoryEntity(directoryEntity);
        userRepository.save(userEntity);
    }

    @Override
    public UserEntity findUserByEmailId(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void updateUser(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    @Override
    public void deleteUser(UserEntity userEntity) {
        userRepository.delete(userEntity);
    }

    @Override
    @UniqueConstraintException
    public void registerUser(SignUpRequestDTO signUpRequest) throws Exception {
        UserEntity userEntity = new UserEntity(signUpRequest.getUsername(), signUpRequest.getPassword(), signUpRequest.getEmail());
        addUser(userEntity);
    }
}
