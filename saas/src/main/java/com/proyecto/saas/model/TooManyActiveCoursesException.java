package com.proyecto.saas.model;

public class TooManyActiveCoursesException extends RuntimeException {
    public TooManyActiveCoursesException(String message) {
        super(message);
    }
}