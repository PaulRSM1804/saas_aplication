package com.proyecto.saas.mapper;

import org.springframework.stereotype.Component;

import com.proyecto.saas.dto.CourseDTO;
import com.proyecto.saas.model.Course;
import com.proyecto.saas.model.CourseStatus;

@Component
public class CourseMapper {
    public CourseDTO toDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setStatus(course.getStatus().name());
        dto.setCreatorId(course.getCreator().getId());
        dto.setImageUrl(course.getImageUrl());
        dto.setCreatorName(course.getCreator().getFirstName());
        return dto;
    }

    public Course toEntity(CourseDTO dto) {
        Course course = new Course();
        course.setId(dto.getId());
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setStatus(CourseStatus.valueOf(dto.getStatus()));
        // El creador se asigna en el servicio
        return course;
    }
}
