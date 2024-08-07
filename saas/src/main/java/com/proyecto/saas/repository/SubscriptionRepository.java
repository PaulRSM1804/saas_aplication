package com.proyecto.saas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.saas.model.Course;
import com.proyecto.saas.model.Subscription;
import com.proyecto.saas.model.User;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByConsumer(User consumer);
    List<Subscription> findByCourse(Course course);

}
