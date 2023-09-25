package com.geethamsoft.NearByJobs.model;

import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "jobs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Job {
    @Id
    private String id;

    @NotBlank(message = "Job title is required")
    private String jobTitle;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Job type is required")
    private String jobType;

    @NotBlank(message = "Experience level is required")
    private String experienceLevel;

    @NotNull(message = "Salary is required")
    @DecimalMin(value = "0.0", message = "Salary must be a non-negative number")
    private double salary;

    @NotBlank(message = "Description is required")
    private String description;

    private String requirements;

    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotBlank(message = "Company industry is required")
    private String companyIndustry;

    @NotBlank(message = "Company size is required")
    private String companySize;

    @ElementCollection
    private List<String> skills;

    private String educationLevel;

    @ElementCollection
    private List<String> jobTags;

    @ElementCollection
    private List<String> benefits;

    @NotBlank(message = "Employment status is required")
    private String employmentStatus;

    @NotBlank(message = "Application type is required")
    private String applicationType;

    @PastOrPresent(message = "Job posting start date must be in the past or present")
    private Date jobPostingStartDate;

    @FutureOrPresent(message = "Job posting end date must be in the future or present")
    private Date jobPostingEndDate;

    private boolean remoteWork;

    @Future(message = "Apply deadline must be in the future")
    private Date applyDeadline;

    private String languageRequirements;

}

