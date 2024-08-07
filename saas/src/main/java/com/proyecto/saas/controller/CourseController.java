package com.proyecto.saas.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.proyecto.saas.dto.CourseDTO;
import com.proyecto.saas.dto.SubscriptionDTO;
import com.proyecto.saas.model.CourseApprovalStatus;
import com.proyecto.saas.model.User;
import com.proyecto.saas.service.CourseService;
import com.proyecto.saas.service.UserService;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "http://localhost:4200")
public class CourseController {
    private final CourseService courseService;
    private final UserService userService;

    public CourseController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<CourseDTO> createCourse(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("status") String status,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        User currentUser = getCurrentUser();
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setTitle(title);
        courseDTO.setDescription(description);
        courseDTO.setStatus(status);

        if (image != null && !image.isEmpty()) {
            String imageUrl = courseService.saveImage(image);
            courseDTO.setImageUrl(imageUrl);
        }

        CourseDTO newCourse = courseService.createCourse(courseDTO, currentUser);
        return ResponseEntity.ok(newCourse);
    }

    @GetMapping("/creator/{creatorId}")
    public ResponseEntity<List<CourseDTO>> getCoursesByCreator(@PathVariable Long creatorId) {
        List<CourseDTO> courses = courseService.getCoursesByCreator(creatorId);
        return ResponseEntity.ok(courses);
    }
 
    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        List<CourseDTO> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<CourseDTO>> getPendingCourses() {
        List<CourseDTO> courses = courseService.getCoursesByApprovalStatus(CourseApprovalStatus.PENDING);
        return ResponseEntity.ok(courses);
    }

    @PutMapping("/approve/{courseId}")
    public ResponseEntity<Void> approveCourse(@PathVariable Long courseId) {
        courseService.approveCourse(courseId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/reject/{courseId}")
    public ResponseEntity<Void> rejectCourse(@PathVariable Long courseId) {
        courseService.rejectCourse(courseId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{courseId}/subscriptions")
    public ResponseEntity<List<SubscriptionDTO>> getCourseSubscriptions(@PathVariable Long courseId) {
        List<SubscriptionDTO> subscriptions = courseService.getCourseSubscriptions(courseId);
        return ResponseEntity.ok(subscriptions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long id, @RequestBody CourseDTO courseDTO) {
        CourseDTO updatedCourse = courseService.updateCourse(id, courseDTO);
        return ResponseEntity.ok(updatedCourse);
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        try {
            courseService.deleteCourse(id);
            return ResponseEntity.ok().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No se puede borrar cursos con usuarios registrados al mismo.");
        }
    }

    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path file = Paths.get("upload-dir").resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }
        Optional<User> userOptional = userService.findByEmail(email);
        return userOptional.orElseThrow(() -> new RuntimeException("User not found"));
    }
}
