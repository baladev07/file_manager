package com.filemanager.appconfig;

import com.filemanager.model.UserEntity;

public class EntityContextHolder {

    private static ThreadLocal<EntityContext> contextHolder = new ThreadLocal<>();

    protected static void setEntityContext(EntityContext entityContext) {
        contextHolder.set(entityContext);
    }

    public static EntityContext getEntityContext() {
        return contextHolder.get();
    }

    public static void destroy() {
        contextHolder.remove();
    }

}
