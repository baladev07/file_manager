package com.filemanager.dto;


import com.filemanager.custom_annotations.PasswordValidator;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@PasswordValidator
public class SignUpRequestDTO {

    private String username;

    private String email;

    private String password;

    private String matchingPassword;





}
