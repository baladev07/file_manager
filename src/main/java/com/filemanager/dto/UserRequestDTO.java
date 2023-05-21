package com.filemanager.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.filemanager.model.UserEntity;
import lombok.Getter;

@Getter
public class UserRequestDTO {

    private String username;

    @JsonProperty("user_email")
    private String userEmail;

    private String password;


    public UserEntity geyUserEntity()
    {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setEmail(userEmail);
        userEntity.setPassword(password);
        return  userEntity;
    }
}
