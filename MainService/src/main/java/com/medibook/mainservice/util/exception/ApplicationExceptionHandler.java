package com.medibook.mainservice.util.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {

    @ExceptionHandler(value = ApplicationException.class)
    public ResponseEntity<ApplicationErrorMessage> handleApplicationException(ApplicationException e){
        log.error(e.getMessage());
        return new ResponseEntity<ApplicationErrorMessage>(
                new ApplicationErrorMessage(e.getCode(),e.getMessage()),
                HttpStatus.valueOf(e.getResponseCode())
        );
    }
}

