package com.filemanager.appconfig;

import com.filemanager.dto.FileDetailsDTO;
import com.filemanager.exception.BadRequestException;
import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CustomMultipartDataResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return FileDetailsDTO.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        FileDetailsDTO fileDetailsDTO = null;
        if (servletRequest instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) servletRequest;
            MultipartFile multipartFile =  null;
            Iterator<String> iterator = multipartRequest.getFileNames();
            String dirName = servletRequest.getParameter("dir_name");
            if(dirName==null || dirName.isEmpty())
            {
                dirName = "root";
            }
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                multipartFile = multipartRequest.getFile(key);
            }
            String fileName = multipartFile.getOriginalFilename();
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            if (!FileDetailsDTO.ALLOWED_EXTENSIONS.contains(fileExtension)) {
                throw new BadRequestException("Unsupported file type");
            }
            fileDetailsDTO = FileDetailsDTO.builder()
                    .multipartFile(multipartFile)
                    .dirName(dirName)
                    .build();
        }
        return fileDetailsDTO;
    }
}
