package com.proyecto.saas.dto;


import java.time.LocalDateTime;

public class SubscriptionDTO {
    private Long id;
    private Long consumerId;
    private Long courseId;
    private LocalDateTime subscriptionDate;
    private String consumerName; // Nuevo campo
    private String courseName; // Nuevo campo
    private String creatorName; // Nuevo campo



    // Constructores

    public SubscriptionDTO() {
    }

    public SubscriptionDTO(Long id, Long consumerId, Long courseId, LocalDateTime subscriptionDate, String consumerName, String courseName, String creatorName) {
        this.id = id;
        this.consumerId = consumerId;
        this.courseId = courseId;
        this.subscriptionDate = subscriptionDate;
        this.consumerName = consumerName;
        this.courseName = courseName;
        this.creatorName = creatorName;
    }


    // Getters y Setters

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Long consumerId) {
        this.consumerId = consumerId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public LocalDateTime getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(LocalDateTime subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    
}
