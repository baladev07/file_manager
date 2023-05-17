package com.filemanager.appconfig;

import com.filemanager.dto.FileDetailsDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;


@Configuration
public class MultipartInterceptor implements HandlerInterceptor {





    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request instanceof MultipartHttpServletRequest) {
            // Multipart request intercepted
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile multipartFile =  null;
            Iterator<String> iterator = multipartRequest.getFileNames();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                multipartFile = (CommonsMultipartFile) multipartRequest.getFile(key);
            }
        }
        return true;
    }


//    @Bean
//    public FileDetailsDTO fileDetailsDTO(String name,MultipartFile multipartFile,String fileFormat,String dirName)
//    {
//        return new FileDetailsDTO(multipartFile,name,fileFormat,dirName);
//    }



}
