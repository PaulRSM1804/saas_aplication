package com.proyecto.saas.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "consumer_id")
    private User consumer;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    private LocalDateTime subscriptionDate;
    @ManyToOne
    @JoinColumn(name = "firts_name")
    private User consumerName;
    @ManyToOne
    @JoinColumn(name = "title")
    private Course courseName;

    // Constructores

    public Subscription() {
    }

    public Subscription(Long id, User consumer, Course course, LocalDateTime subscriptionDate, User consumerName, Course courseName) {
        this.id = id;
        this.consumer = consumer;
        this.course = course;
        this.subscriptionDate = subscriptionDate;
        this.consumerName = consumerName;
        this.courseName = courseName;
    }
    // Getters y Setters

    public Course getCourseName() {
        return courseName;
    }

    public void setCourseName(Course courseName) {
        this.courseName = courseName;
    }

    public User getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(User consumerName) {
        this.consumerName = consumerName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getConsumer() {
        return consumer;
    }

    public void setConsumer(User consumer) {
        this.consumer = consumer;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDateTime getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(LocalDateTime subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    
}
