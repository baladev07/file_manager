package com.filemanager.appconfig;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.filemanager.dto.DirectoryRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class JacksonConfig {



//    @Bean
//    public ObjectMapper objectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        SimpleModule module = new SimpleModule();
//        module.addDeserializer(DirectoryRequestDTO.class, new DirectoryRequestDTODeserializer());
//        objectMapper.registerModule(module);
//        return objectMapper;
//    }

}
