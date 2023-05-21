package com.filemanager.controller;


import com.filemanager.Util.JWTOauthTokenUtil;
import com.filemanager.dto.*;
import com.filemanager.http.HttpResponseBuilder;
import com.filemanager.model.UserEntity;
import com.filemanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping("/api")
public class UserController {

    @Autowired
    HttpResponseBuilder httpResponseBuilder;

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JWTOauthTokenUtil jwtOauthTokenUtil;

    @PostMapping("/user")
    public ResponseEntity addUser(@RequestBody UserRequestDTO userRequestDTO) throws Exception {
        UserEntity userEntity = userRequestDTO.geyUserEntity();
        userService.addUser(userEntity);
        return ResponseEntity.ok(httpResponseBuilder.message("user added successfully").build());
    }


}
