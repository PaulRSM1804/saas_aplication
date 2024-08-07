package com.proyecto.saas.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.proyecto.saas.dto.CourseDTO;
import com.proyecto.saas.dto.SubscriptionDTO;
import com.proyecto.saas.exception.ResourceNotFoundException;
import com.proyecto.saas.mapper.CourseMapper;
import com.proyecto.saas.mapper.SubscriptionMapper;
import com.proyecto.saas.model.Course;
import com.proyecto.saas.model.CourseApprovalStatus;
import com.proyecto.saas.model.CourseStatus;
import com.proyecto.saas.model.Subscription;
import com.proyecto.saas.model.TooManyActiveCoursesException;
import com.proyecto.saas.model.User;
import com.proyecto.saas.repository.CourseRepository;
import com.proyecto.saas.repository.SubscriptionRepository;
import com.proyecto.saas.repository.UserRepository;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CourseMapper courseMapper;
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final Path rootLocation = Paths.get("upload-dir");

    public CourseService(CourseRepository courseRepository, UserRepository userRepository, CourseMapper courseMapper, SubscriptionRepository subscriptionRepository, SubscriptionMapper subscriptionMapper) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.courseMapper = courseMapper;
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionMapper = subscriptionMapper;
    }

    public CourseDTO createCourse(CourseDTO courseDTO, User creator) {
        CourseStatus courseStatus = CourseStatus.fromString(courseDTO.getStatus());
    
        // Verificar el número de cursos activos del creador
        if (courseStatus == CourseStatus.ACTIVE) {
            long activeCourses = courseRepository.countByCreatorIdAndStatus(creator.getId(), CourseStatus.ACTIVE);
            if (activeCourses >= 2) {
                throw new TooManyActiveCoursesException("Creators can only have a maximum of two active courses");
            }
        }
    
        // Convertir DTO a entidad
        Course course = courseMapper.toEntity(courseDTO);
        course.setCreator(creator);
        course.setStatus(courseStatus);
        course.setApprovalStatus(CourseApprovalStatus.PENDING); // Establecer el estado de aprobación como pendiente
        course.setImageUrl(courseDTO.getImageUrl());
        
        Course savedCourse = courseRepository.save(course);
        return courseMapper.toDTO(savedCourse);
    }

    public List<CourseDTO> getCoursesByApprovalStatus(CourseApprovalStatus approvalStatus) {
        return courseRepository.findByApprovalStatus(approvalStatus).stream()
                .map(courseMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<CourseDTO> getPendingCourses() {
        return courseRepository.findByApprovalStatus(CourseApprovalStatus.PENDING).stream()
            .map(courseMapper::toDTO)
            .collect(Collectors.toList());
    }

    public void approveCourse(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        course.setApprovalStatus(CourseApprovalStatus.APPROVED);
        courseRepository.save(course);
    }

    public void rejectCourse(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        course.setApprovalStatus(CourseApprovalStatus.REJECTED);
        courseRepository.save(course);
    }

    public List<CourseDTO> getCoursesByCreator(Long creatorId) {
        User creator = userRepository.findById(creatorId).orElseThrow(() -> new ResourceNotFoundException("Creator not found"));
        return courseRepository.findByCreator(creator).stream().map(courseMapper::toDTO).collect(Collectors.toList());
    }

    public List<SubscriptionDTO> getCourseSubscriptions(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        List<Subscription> subscriptions = subscriptionRepository.findByCourse(course);
        return subscriptions.stream().map(subscriptionMapper::toDTO).collect(Collectors.toList());
    }

    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream().map(course -> {
            CourseDTO courseDTO = courseMapper.toDTO(course);
            courseDTO.setCreatorName(course.getCreator().getFirstName() + " " + course.getCreator().getLastName()); // Asignar el nombre del creador
            courseDTO.setStatus(course.getStatus().toString()); // Asignar el estado del curso
            courseDTO.setApprovalStatus(course.getApprovalStatus().toString()); // Asignar el estado de aprobación del curso
            return courseDTO;
        }).collect(Collectors.toList());
    }

    public CourseDTO updateCourse(Long id, CourseDTO courseDTO) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());
        course.setStatus(CourseStatus.valueOf(courseDTO.getStatus()));
        return courseMapper.toDTO(courseRepository.save(course));
    }

    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        courseRepository.delete(course);
    }

    public String saveImage(MultipartFile file) {
        try {
            if (!Files.exists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }
            Path destinationFile = rootLocation.resolve(Paths.get(file.getOriginalFilename())).normalize().toAbsolutePath();
            file.transferTo(destinationFile);
            return "/api/images/" + file.getOriginalFilename(); // Ruta relativa al controlador
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }
}
