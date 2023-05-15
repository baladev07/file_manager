package com.filemanager.http;

import java.util.LinkedHashMap;
import java.util.Map;

public class HttpResponseBuilder {

    private Map<String, Object> dataMap;

    private Map<String,Object>valMap;

    public HttpResponseBuilder()
    {
        dataMap = new LinkedHashMap<>();
    }

    public HttpResponseBuilder message(String message) {
        dataMap.put("message",message);
        return this;
    }


    public HttpResponseBuilder data(Object data)
    {
        dataMap.put("data", data);
        return this;
    }

    public HttpResponseBuilder data(String key,Object obj)
    {
        valMap = new LinkedHashMap<>();
        valMap.put(key,obj);
        dataMap.put("data",valMap);
        return this;
    }



    public HttpResponseBuilder add(String key,Object obj)
    {
        dataMap.put(key,obj);
        return this;
    }


    public Map<String, Object> build()
    {
        return dataMap;
    }

}
