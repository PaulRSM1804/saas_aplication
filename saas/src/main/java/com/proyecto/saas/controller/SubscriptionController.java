package com.proyecto.saas.controller;

import java.util.List;

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

import com.proyecto.saas.dto.SubscriptionDTO;
import com.proyecto.saas.model.User;
import com.proyecto.saas.model.UserType;
import com.proyecto.saas.repository.UserRepository;
import com.proyecto.saas.service.SubscriptionService;


@RestController
@RequestMapping("/api/subscriptions")
@CrossOrigin(origins = "http://localhost:4200")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;
    private final UserRepository userRepository;


    public SubscriptionController(SubscriptionService subscriptionService, UserRepository userRepository) {
        this.subscriptionService = subscriptionService;
        this.userRepository = userRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<SubscriptionDTO> createSubscription(@RequestParam Long consumerId, @RequestParam Long courseId) {
        SubscriptionDTO newSubscription = subscriptionService.createSubscription(consumerId, courseId);
        return ResponseEntity.ok(newSubscription);
    }
    

    @GetMapping("/consumer/{consumerId}")
    public ResponseEntity<List<SubscriptionDTO>> getSubscriptionsByConsumer(@PathVariable Long consumerId) {
        List<SubscriptionDTO> subscriptions = subscriptionService.getSubscriptionsByConsumer(consumerId);
        return ResponseEntity.ok(subscriptions);
    }


    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<SubscriptionDTO>> getSubscriptionsByCourse(@PathVariable Long courseId) {
        List<SubscriptionDTO> subscriptions = subscriptionService.getSubscriptionsByCourse(courseId);
        return ResponseEntity.ok(subscriptions);
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionDTO>> getSubscriptions() {
        User user = getCurrentUser();
        if (user.getUserType() == UserType.ADMIN) {
            return ResponseEntity.ok(subscriptionService.getAllSubscriptions());
        } else {
            return ResponseEntity.ok(subscriptionService.getSubscriptionsByConsumer(user.getId()));
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
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @GetMapping("/creator/{creatorId}")
    public ResponseEntity<List<SubscriptionDTO>> getSubscriptionsByCreator(@PathVariable Long creatorId) {
        List<SubscriptionDTO> subscriptions = subscriptionService.getSubscriptionsByCreator(creatorId);
        return ResponseEntity.ok(subscriptions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionDTO> updateSubscription(@PathVariable Long id, @RequestBody SubscriptionDTO subscriptionDTO) {
        SubscriptionDTO updatedSubscription = subscriptionService.updateSubscription(id, subscriptionDTO);
        return ResponseEntity.ok(updatedSubscription);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id) {
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.noContent().build();
    }
}
