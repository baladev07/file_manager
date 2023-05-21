package com.filemanager.controller;


import com.filemanager.Util.JWTOauthTokenUtil;
import com.filemanager.dto.JwtResponseDTO;
import com.filemanager.dto.LoginRequestDTO;
import com.filemanager.dto.SignUpRequestDTO;
import com.filemanager.http.HttpResponseBuilder;
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
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JWTOauthTokenUtil jwtOauthTokenUtil;

    @Autowired
    HttpResponseBuilder httpResponseBuilder;

    @PostMapping("/signup")
    public ResponseEntity userSignUp(@Valid @RequestBody SignUpRequestDTO request) throws Exception {
        userService.registerUser(request);
        return ResponseEntity.ok(httpResponseBuilder.message("User registered successfully").build());
    }

    @PostMapping("/signin")
    public ResponseEntity userSignIn(@Valid @RequestBody LoginRequestDTO loginRequest)
    {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        final String token = jwtOauthTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponseDTO(token));
    }

}
