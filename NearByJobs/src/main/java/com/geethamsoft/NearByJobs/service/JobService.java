package com.geethamsoft.NearByJobs.service;



import com.geethamsoft.NearByJobs.dto.JobDTO;
import com.geethamsoft.NearByJobs.dto.JobSearchDTO;
import com.geethamsoft.NearByJobs.exception.ResourceNotFoundException;
import com.geethamsoft.NearByJobs.model.Job;
import com.geethamsoft.NearByJobs.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public List<Job> searchJobs(JobSearchDTO searchDTO) {
        Query query = buildSearchQuery(searchDTO);
        return mongoTemplate.find(query, Job.class);
    }

    private Query buildSearchQuery(JobSearchDTO searchDTO) {
        Query query = new Query();

        if (searchDTO.getKeyword() != null && !searchDTO.getKeyword().isEmpty()) {
            Criteria keywordCriteria = new Criteria().orOperator(
                    Criteria.where("jobTitle").regex(searchDTO.getKeyword(), "i"),
                    Criteria.where("category").regex(searchDTO.getKeyword(), "i"),
                    Criteria.where("location").regex(searchDTO.getKeyword(), "i"),
                    Criteria.where("description").regex(searchDTO.getKeyword(), "i"),
                    Criteria.where("companyName").regex(searchDTO.getKeyword(), "i")
            );
            query.addCriteria(keywordCriteria);
        }

        if (searchDTO.getCategory() != null && !searchDTO.getCategory().isEmpty()) {
            query.addCriteria(Criteria.where("category").is(searchDTO.getCategory()));
        }

        if (searchDTO.getLocation() != null && !searchDTO.getLocation().isEmpty()) {
            query.addCriteria(Criteria.where("location").is(searchDTO.getLocation()));
        }

        if (searchDTO.getJobType() != null && !searchDTO.getJobType().isEmpty()) {
            query.addCriteria(Criteria.where("jobType").is(searchDTO.getJobType()));
        }

        if (searchDTO.getExperienceLevel() != null && !searchDTO.getExperienceLevel().isEmpty()) {
            query.addCriteria(Criteria.where("experienceLevel").is(searchDTO.getExperienceLevel()));
        }

        if (searchDTO.getMinSalary() != null) {
            query.addCriteria(Criteria.where("salary").gte(searchDTO.getMinSalary()));
        }

        if (searchDTO.getMaxSalary() != null) {
            query.addCriteria(Criteria.where("salary").lte(searchDTO.getMaxSalary()));
        }

        if (searchDTO.getJobPostingStartDate() != null) {
            query.addCriteria(Criteria.where("jobPostingStartDate").gte(searchDTO.getJobPostingStartDate()));
        }

        if (searchDTO.getJobPostingEndDate() != null) {
            query.addCriteria(Criteria.where("jobPostingEndDate").lte(searchDTO.getJobPostingEndDate()));
        }

        if (searchDTO.isRemoteWork()) {
            query.addCriteria(Criteria.where("remoteWork").is(true));
        }

        if (searchDTO.getApplyDeadline() != null) {
            query.addCriteria(Criteria.where("applyDeadline").gte(searchDTO.getApplyDeadline()));
        }

        if (searchDTO.getLanguageRequirements() != null && !searchDTO.getLanguageRequirements().isEmpty()) {
            query.addCriteria(Criteria.where("languageRequirements").is(searchDTO.getLanguageRequirements()));
        }

        // Add more criteria for the remaining fields as needed

        return query;
    }


    public Job addJob(JobDTO jobDTO) {
        Job job = mapJobDTO(jobDTO);
        return jobRepository.save(job);
    }

    public Job updateJob(String id, JobDTO jobDTO) throws ResourceNotFoundException {
        Job existingJob = getJobById(id);
        Job updatedJob = mapJobDTO(jobDTO);

        existingJob.setJobTitle(updatedJob.getJobTitle());
        existingJob.setCategory(updatedJob.getCategory());
        existingJob.setLocation(updatedJob.getLocation());
        existingJob.setJobType(updatedJob.getJobType());
        existingJob.setExperienceLevel(updatedJob.getExperienceLevel());
        existingJob.setSalary(updatedJob.getSalary());
        existingJob.setDescription(updatedJob.getDescription());
        existingJob.setRequirements(updatedJob.getRequirements());
        existingJob.setCompanyName(updatedJob.getCompanyName());
        existingJob.setCompanyIndustry(updatedJob.getCompanyIndustry());
        existingJob.setCompanySize(updatedJob.getCompanySize());
        existingJob.setSkills(updatedJob.getSkills());
        existingJob.setEducationLevel(updatedJob.getEducationLevel());
        existingJob.setJobTags(updatedJob.getJobTags());
        existingJob.setBenefits(updatedJob.getBenefits());
        existingJob.setEmploymentStatus(updatedJob.getEmploymentStatus());
        existingJob.setApplicationType(updatedJob.getApplicationType());
        existingJob.setJobPostingStartDate(updatedJob.getJobPostingStartDate());
        existingJob.setJobPostingEndDate(updatedJob.getJobPostingEndDate());
        existingJob.setRemoteWork(updatedJob.isRemoteWork());
        existingJob.setApplyDeadline(updatedJob.getApplyDeadline());
        existingJob.setLanguageRequirements(updatedJob.getLanguageRequirements());

        return jobRepository.save(existingJob);
    }

    public void deleteJob(String id) throws ResourceNotFoundException {
        Job existingJob = getJobById(id);
        jobRepository.delete(existingJob);
    }

    private Job getJobById(String id) throws ResourceNotFoundException {
        Optional<Job> jobOptional = jobRepository.findById(id);
        if (jobOptional.isPresent()) {
            return jobOptional.get();
        } else {
            throw new ResourceNotFoundException("Job not found with id: " + id);
        }
    }

    private Job mapJobDTO(JobDTO jobDTO) {
        Job job = new Job();
        job.setJobTitle(jobDTO.getJobTitle());
        job.setCategory(jobDTO.getCategory());
        job.setLocation(jobDTO.getLocation());
        job.setJobType(jobDTO.getJobType());
        job.setExperienceLevel(jobDTO.getExperienceLevel());
        job.setSalary(jobDTO.getSalary());
        job.setDescription(jobDTO.getDescription());
        job.setRequirements(jobDTO.getRequirements());
        job.setCompanyName(jobDTO.getCompanyName());
        job.setCompanyIndustry(jobDTO.getCompanyIndustry());
        job.setCompanySize(jobDTO.getCompanySize());
        job.setSkills(jobDTO.getSkills());
        job.setEducationLevel(jobDTO.getEducationLevel());
        job.setJobTags(jobDTO.getJobTags());
        job.setBenefits(jobDTO.getBenefits());
        job.setEmploymentStatus(jobDTO.getEmploymentStatus());
        job.setApplicationType(jobDTO.getApplicationType());
        job.setJobPostingStartDate(jobDTO.getJobPostingStartDate());
        job.setJobPostingEndDate(jobDTO.getJobPostingEndDate());
        job.setRemoteWork(jobDTO.isRemoteWork());
        job.setApplyDeadline(jobDTO.getApplyDeadline());
        job.setLanguageRequirements(jobDTO.getLanguageRequirements());
        return job;
    }
}

