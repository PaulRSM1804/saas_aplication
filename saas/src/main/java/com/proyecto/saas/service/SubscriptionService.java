package com.proyecto.saas.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.proyecto.saas.dto.SubscriptionDTO;
import com.proyecto.saas.exception.ResourceNotFoundException;
import com.proyecto.saas.mapper.SubscriptionMapper;
import com.proyecto.saas.model.Course;
import com.proyecto.saas.model.Subscription;
import com.proyecto.saas.model.SubscriptionException;
import com.proyecto.saas.model.User;
import com.proyecto.saas.repository.CourseRepository;
import com.proyecto.saas.repository.SubscriptionRepository;
import com.proyecto.saas.repository.UserRepository;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final SubscriptionMapper subscriptionMapper;

    public SubscriptionService(SubscriptionRepository subscriptionRepository, UserRepository userRepository, CourseRepository courseRepository, SubscriptionMapper subscriptionMapper) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.subscriptionMapper = subscriptionMapper;
    }

    public SubscriptionDTO createSubscription(Long consumerId, Long courseId) {
        User consumer = userRepository.findById(consumerId).orElseThrow(() -> new ResourceNotFoundException("Consumer not found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        // Verificar si el consumidor ya está suscrito a otro curso del mismo creador
        List<Subscription> existingSubscriptions = subscriptionRepository.findByConsumer(consumer);
        boolean alreadySubscribedToCreator = existingSubscriptions.stream()
            .anyMatch(subscription -> subscription.getCourse().getCreator().getId().equals(course.getCreator().getId()));
        if (alreadySubscribedToCreator) {
            throw new SubscriptionException("Consumers can only subscribe to one course per creator at a time.");
        }

        Subscription subscription = new Subscription();
        subscription.setConsumer(consumer);
        subscription.setCourse(course);
        subscription.setSubscriptionDate(LocalDateTime.now());

        SubscriptionDTO subscriptionDTO = subscriptionMapper.toDTO(subscriptionRepository.save(subscription));
        subscriptionDTO.setConsumerName(consumer.getFirstName() + " " + consumer.getLastName());
        subscriptionDTO.setCourseName(course.getTitle());

        return subscriptionDTO;
    }

    public List<SubscriptionDTO> getSubscriptionsByCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        List<Subscription> subscriptions = subscriptionRepository.findByCourse(course);
        return subscriptions.stream().map(subscriptionMapper::toDTO).collect(Collectors.toList());
    }

    public List<SubscriptionDTO> getSubscriptionsByConsumer(Long consumerId) {
        User consumer = userRepository.findById(consumerId).orElseThrow(() -> new ResourceNotFoundException("Consumer not found"));
        return subscriptionRepository.findByConsumer(consumer).stream().map(subscription -> {
            SubscriptionDTO dto = subscriptionMapper.toDTO(subscription);
            dto.setConsumerName(consumer.getFirstName() + " " + consumer.getLastName());
            dto.setCourseName(subscription.getCourse().getTitle());
            return dto;
        }).collect(Collectors.toList());
    }

    public List<SubscriptionDTO> getSubscriptionsByCreator(Long creatorId) {
        User creator = userRepository.findById(creatorId).orElseThrow(() -> new ResourceNotFoundException("Creator not found"));
        List<Course> courses = courseRepository.findByCreator(creator);
        List<Subscription> subscriptions = courses.stream()
                .flatMap(course -> subscriptionRepository.findByCourse(course).stream())
                .collect(Collectors.toList());

        return subscriptions.stream().map(subscription -> {
            SubscriptionDTO dto = subscriptionMapper.toDTO(subscription);
            dto.setConsumerName(subscription.getConsumer().getFirstName() + " " + subscription.getConsumer().getLastName());
            dto.setCourseName(subscription.getCourse().getTitle());
            return dto;
        }).collect(Collectors.toList());
    }

    public List<SubscriptionDTO> getAllSubscriptions() {
        return subscriptionRepository.findAll().stream().map(subscription -> {
            SubscriptionDTO dto = subscriptionMapper.toDTO(subscription);
            dto.setConsumerName(subscription.getConsumer().getFirstName() + " " + subscription.getConsumer().getLastName());
            dto.setCourseName(subscription.getCourse().getTitle());
            return dto;
        }).collect(Collectors.toList());
    }

    public SubscriptionDTO updateSubscription(Long id, SubscriptionDTO subscriptionDTO) {
        Subscription subscription = subscriptionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));
        subscription.setSubscriptionDate(subscriptionDTO.getSubscriptionDate());
        return subscriptionMapper.toDTO(subscriptionRepository.save(subscription));
    }

    public void deleteSubscription(Long id) {
        Subscription subscription = subscriptionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));
        subscriptionRepository.delete(subscription);
    }
}
