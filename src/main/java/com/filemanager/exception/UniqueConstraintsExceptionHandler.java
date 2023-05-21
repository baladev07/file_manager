package com.filemanager.exception;

import com.filemanager.appconfig.AppConfig;
import org.hibernate.SQLQuery;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;


import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UniqueConstraintsExceptionHandler {

    @Autowired
    EntityManager entityManager;
    public void handleConstraintViolation(Throwable ex) {

        String databaseName = AppConfig.getDatabaseName(); // Get the table name
        String exceptionMessage = ex.getLocalizedMessage();
        Pattern pattern = Pattern.compile("for key '(.+)'");
        Matcher matcher = pattern.matcher(exceptionMessage);
        String constraintName = "";
        if (matcher.find()) {
            constraintName = matcher.group(1);
        }
        String query = "SELECT COLUMN_NAME " +
                "FROM information_schema.KEY_COLUMN_USAGE " +
                "WHERE CONSTRAINT_NAME = :constraintName " +
                "AND CONSTRAINT_SCHEMA = :databaseName";

            Query sqlQuery =  entityManager.createNativeQuery(query);
            sqlQuery.setParameter("constraintName",constraintName);
            sqlQuery.setParameter("databaseName",databaseName);
            List<String> columnNames = sqlQuery.getResultList();

            if(!columnNames.isEmpty())
            {
                throw new BadRequestException(columnNames.get(0)+" already exist.");
            }
    }
}
