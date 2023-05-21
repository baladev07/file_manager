package com.filemanager.appconfig;

import com.filemanager.model.BaseEntity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
@Scope("prototype")
public class EntityContext {

    private Map<Class<?>, Object> entityInstancesMap;

    protected void initialize()
    {
        entityInstancesMap = new HashMap<>();
    }

//    public void destroy()
//    {
//        entityInstancesMap.clear();
//    }

    protected <T>void registerEntity(Class<T>entityType, BaseEntity entity)
    {
        entityInstancesMap.put(entityType,entity);

    }

    public <T> T getEntity(Class<T>entityType)
    {
        return entityType.cast(entityInstancesMap.get(entityType));
    }

}
