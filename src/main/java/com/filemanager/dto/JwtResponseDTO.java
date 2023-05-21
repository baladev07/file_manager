package com.filemanager.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JwtResponseDTO {

    private String access_token;

    private String refresh_token;

    public JwtResponseDTO(String accessToken) {
        this.access_token = accessToken;
    }
}
