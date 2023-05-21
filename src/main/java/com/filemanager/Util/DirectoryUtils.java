package com.filemanager.Util;

public class DirectoryUtils {

    public static String CURR_PATH = System.getProperty("user.home");

    public static String rootPathName = "root";

    public static String rootPth = "/"+rootPathName;

    public static String absRootPath = System.getProperty("user.home")+"/"+rootPathName;




    public static String getAbsolutePath(String name,String path)
    {
        return CURR_PATH+path+"/"+name;
    }

//    public static String getAbsolutePath(String parentDir,String currDir)
//    {
//        return CURR_PATH+"/"+parentDir+currDir;
//    }



}
