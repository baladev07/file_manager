package com.filemanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class LoginRequestDTO {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
