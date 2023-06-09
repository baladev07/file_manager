package com.filemanager.appconfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        logger.info("Request reached inside a authentication entry point.");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "Access Denied: Authentication Required. Please provide a valid access token to access this resource.");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        if (authException instanceof InvalidBearerTokenException) {
            logger.error("Custom exception log for un authorized request{}", authException.getMessage());
            jsonObject.put("message", "Invalid access token");
        }
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(jsonObject));
    }
}
