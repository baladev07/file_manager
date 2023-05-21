package com.filemanager.exception.handler;

import com.filemanager.exception.BadRequestException;
import com.filemanager.http.HttpResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;


@ControllerAdvice
public class ControllerExceptionHandler  {

    @Autowired HttpResponseBuilder httpResponseBuilder;

    Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);


//    @ExceptionHandler(BadRequestException.class)
//    public ResponseEntity badRequestHandler(Exception ex)
//    {
//        return ResponseEntity.badRequest().body(new HttpResponseBuilder().message(ex.getMessage()).build());
//    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return ResponseEntity.badRequest().body(httpResponseBuilder.message(ex.getMessage()).build());
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleControllerException(Exception ex) {

        if(ex instanceof HttpRequestMethodNotSupportedException)
        {
            return ResponseEntity.badRequest().body(httpResponseBuilder.message(ex.getMessage()).build());
        }
        else if (ex instanceof MultipartException) {
            logger.error("Exception occurred in /upload api.",ex);
            return ResponseEntity.badRequest().body(httpResponseBuilder.message(ex.getMessage()).build());
        }

        else if(ex instanceof BadRequestException)
        {
            return ResponseEntity.badRequest().body(new HttpResponseBuilder().message(ex.getMessage()).build());
        }
        else if(ex instanceof ConstraintViolationException)
        {
            return ResponseEntity.badRequest().body(httpResponseBuilder.message("Invalid Id").build());
        }
        else {
            logger.error(ex.getMessage(),ex);
        }
        return ResponseEntity.badRequest().body(httpResponseBuilder.message("Internal error").build());
    }

}
