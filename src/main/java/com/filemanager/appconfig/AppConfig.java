package com.filemanager.appconfig;


import com.filemanager.impl.UserServiceImpl;
import com.filemanager.model.UserEntity;
import com.filemanager.repository.UserRepository;
import com.filemanager.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import javax.annotation.PostConstruct;


@Configuration
public class AppConfig {

    @Value("spring.database.name")
    private String dbNameProp;

    private static String databaseName;
    @PostConstruct
    private void init()
    {
           databaseName = dbNameProp;
    }

    public static String getDatabaseName()
    {
        return databaseName;
    }
}
