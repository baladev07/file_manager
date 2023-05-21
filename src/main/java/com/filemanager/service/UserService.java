package com.filemanager.service;

import com.filemanager.dto.SignUpRequestDTO;
import com.filemanager.model.UserEntity;

import java.util.List;

public interface UserService {

    public void addUser(UserEntity userEntity) throws Exception;

    UserEntity findUserByEmailId(String email);

    List<UserEntity> getAllUsers();

    void updateUser(UserEntity userEntity);

    void deleteUser(UserEntity userEntity);

    void registerUser(SignUpRequestDTO signUpRequest) throws Exception;
}
