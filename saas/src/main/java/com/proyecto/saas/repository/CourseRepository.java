package com.proyecto.saas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.saas.model.Course;
import com.proyecto.saas.model.CourseApprovalStatus;
import com.proyecto.saas.model.CourseStatus;
import com.proyecto.saas.model.User;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    long countByCreatorIdAndStatus(Long creatorId, CourseStatus status);
    List<Course> findByCreator(User creator);
    List<Course> findByCreatorId(Long creatorId);

    long countByCreatorIdAndStatus(Long creatorId, CourseApprovalStatus approvalStatus);

    List<Course> findByApprovalStatus(CourseApprovalStatus approvalStatus);
}
