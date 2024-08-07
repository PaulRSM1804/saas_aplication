package com.proyecto.saas.model;


import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    @Enumerated(EnumType.STRING)
    private CourseStatus status;
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @OneToMany(mappedBy = "course")
    private Set<Subscription> subscriptions;

    @Enumerated(EnumType.STRING)
    private CourseApprovalStatus approvalStatus;

    // Constructores

    public Course() {
    }

    public Course(Long id, String title, String description, CourseStatus status, User creator, String imageUrl, CourseApprovalStatus approvalStatus) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.creator = creator;
        this.imageUrl = imageUrl;
        this.approvalStatus = approvalStatus;
    }

    // Getters y Setters

    public CourseApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(CourseApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CourseStatus getStatus() {
        return status;
    }

    public void setStatus(CourseStatus status) {
        this.status = status;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Set<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Set<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }



}
