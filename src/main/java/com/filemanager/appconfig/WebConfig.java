package com.filemanager.appconfig;

import org.hibernate.Interceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@Configuration
public class WebConfig implements WebMvcConfigurer {

//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new MultipartInterceptor());
//    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        // Add your custom resolver to the list of argument resolvers
        argumentResolvers.add(new CustomMultipartDataResolver());
    }
}
