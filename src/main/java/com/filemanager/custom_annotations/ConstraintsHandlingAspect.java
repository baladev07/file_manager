package com.filemanager.custom_annotations;


import com.filemanager.exception.UniqueConstraintsExceptionHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLIntegrityConstraintViolationException;

@Aspect
@Component
public class ConstraintsHandlingAspect {
   @Autowired
    UniqueConstraintsExceptionHandler handler;
    @Around("@annotation(UniqueConstraintException)")
    public Object handleException(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Exception ex) {
             Throwable nestedException  = ex;
            while(nestedException!=null)
            {
                if(nestedException instanceof SQLIntegrityConstraintViolationException)
                {
                    handler.handleConstraintViolation(nestedException);
                }
                nestedException = nestedException.getCause();
            }
            throw ex;
        }
    }
}
