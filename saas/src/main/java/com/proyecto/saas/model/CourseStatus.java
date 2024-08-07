package com.proyecto.saas.model;

public enum CourseStatus {
    IN_CONSTRUCTION,
    ACTIVE,
    INACTIVE;

    public static CourseStatus fromString(String status) {
        return CourseStatus.valueOf(status.toUpperCase());
    }
}
