package com.filemanager.exception.handler;

import com.filemanager.exception.BadRequestException;
import com.filemanager.http.HttpResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;


@ControllerAdvice
public class ControllerExceptionHandler {

    Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity badRequestHandler(Exception ex)
    {
        return ResponseEntity.badRequest().body(new HttpResponseBuilder().message(ex.getMessage()).build());
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleControllerException(Exception ex) {

        if(ex instanceof HttpRequestMethodNotSupportedException)
        {
            return ResponseEntity.badRequest().body(new HttpResponseBuilder().message(ex.getMessage()).build());
        }
        else if (ex instanceof MultipartException) {
            logger.error("Exception occurred in /upload api.",ex);
            return ResponseEntity.badRequest().body(new HttpResponseBuilder().message(ex.getMessage()).build());
        }
        logger.error(ex.getMessage(),ex);
        return ResponseEntity.badRequest().body(new HttpResponseBuilder().message("Internal error").build());
    }

}
