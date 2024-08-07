package com.proyecto.saas.dto;


public class CourseDTO {
    private Long id;
    private String title;
    private String description;
    private String status;
    private Long creatorId;
    private String imageUrl;
    private String creatorName;  // Añadido
    private String approvalStatus;  // Añadido


    // Constructores

    public CourseDTO() {
    }

    public CourseDTO(Long id, String title, String description, String status, Long creatorId, String imageUrl, String creatorName, String approvalStatus) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.creatorId = creatorId;
        this.imageUrl = imageUrl;
        this.creatorName = creatorName;
        this.approvalStatus = approvalStatus;
    }
    // Getters y Setters

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }
}
