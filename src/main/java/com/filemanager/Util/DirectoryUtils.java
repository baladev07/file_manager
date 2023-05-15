package com.filemanager.Util;

public class DirectoryUtils {

    public static String CURR_PATH = System.getProperty("user.dir");

    public static String rootDir = System.getProperty("user.dir")+"/root";

    public static String root = "root";



    public static String getAbsolutePath(String name)
    {
        return rootDir+"/"+name;
    }

    public static String getAbsolutePath(String parentDir,String currDir)
    {
        return CURR_PATH+"/"+parentDir+currDir;
    }



}
