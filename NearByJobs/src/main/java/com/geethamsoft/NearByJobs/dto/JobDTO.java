package com.geethamsoft.NearByJobs.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobDTO {
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
    @DecimalMin(value = "0.0", inclusive = false, message = "Salary must be a non-negative number")
    private Double salary;

    @NotBlank(message = "Description is required")
    private String description;

    private String requirements;

    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotBlank(message = "Company industry is required")
    private String companyIndustry;

    @NotBlank(message = "Company size is required")
    private String companySize;

    private List<String> skills;

    private String educationLevel;

    private List<String> jobTags;

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

    private String createdAtFormatted;
    private String updatedAtFormatted;
}
