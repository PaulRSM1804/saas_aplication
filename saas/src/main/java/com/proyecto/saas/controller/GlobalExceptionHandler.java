package com.proyecto.saas.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.proyecto.saas.model.SubscriptionException;
import com.proyecto.saas.model.TooManyActiveCoursesException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TooManyActiveCoursesException.class)
    public ResponseEntity<String> handleTooManyActiveCoursesException(TooManyActiveCoursesException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SubscriptionException.class)
    public ResponseEntity<String> handleException(SubscriptionException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Other exception handlers...


}