package com.geethamsoft.NearByJobs.dto;

import lombok.Data;
import java.util.Date;

@Data
public class JobSearchDTO {
    private String keyword;
    private String category;
    private String location;
    private String sortBy;
    private String jobType;
    private String experienceLevel;
    private Double minSalary;
    private Double maxSalary;
    private String employmentStatus;
    private Date jobPostingStartDate;
    private Date jobPostingEndDate;
    private boolean remoteWork;
    private Date applyDeadline;
    private String languageRequirements;
}

