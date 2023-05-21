package com.filemanager.appconfig;

import com.filemanager.model.UserEntity;
import com.filemanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@Component
public class RequestInterceptor implements HandlerInterceptor {

    @Autowired UserRepository userRepository;

    @Autowired EntityContext entityContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getRequestURI().contains("swagger")) {
            return true;
        }

        if(!SecurityValidator.isPublicURI(request.getRequestURI()))
        {
            entityContext.initialize();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Jwt jwt = (Jwt) authentication.getPrincipal();
            String email = jwt.getSubject();
            entityContext.registerEntity(UserEntity.class, userRepository.findByEmail(email));
            EntityContextHolder.setEntityContext(entityContext);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // Perform actions after request processing but before view rendering
        {


        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        EntityContextHolder.destroy();
    }

}
