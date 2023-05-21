package com.filemanager.appconfig;

public class SecurityValidator {


    public static final String[] PUBLIC_PATHS = {
            "/v3/api-docs.yaml",
            "/v3/api-docs/**",
            "/api/signup",
            "/api/signin",
            "/swagger-ui/**",
            "/swagger-ui.html"};

    public static boolean isPublicURI(String requestURI)
    {
        for(String s:PUBLIC_PATHS)
        {
            if(s.contains(requestURI))
            {
                return true;
            }
        }
        return false;
    }
}
