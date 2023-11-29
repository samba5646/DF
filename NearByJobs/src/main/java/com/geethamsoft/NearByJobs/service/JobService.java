package com.geethamsoft.NearByJobs.service;

import com.geethamsoft.NearByJobs.dto.JobDTO;
import com.geethamsoft.NearByJobs.dto.JobSearchDTO;
import com.geethamsoft.NearByJobs.exception.JobNotFoundException;
import com.geethamsoft.NearByJobs.model.Job;
import com.geethamsoft.NearByJobs.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class
JobService {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public Optional<Job> getJobById(String id) {
        if(jobRepository.findById(id).isEmpty()){
            throw new JobNotFoundException("Job not found with id: " + id);
        }
        return jobRepository.findById(id);
    }

    public Job addJob(JobDTO jobDTO) {
        Job job = mapJobDTO(jobDTO);
        return jobRepository.save(job);
    }
    public Optional<Job> updateJob(String id, JobDTO jobDTO)  {
       Optional <Job> existingJob = jobRepository.findById(id);

        if(existingJob.isPresent()){
            Job updatedJob = mapJobDTO(jobDTO);

            updatedJob.setJobTitle(jobDTO.getJobTitle());
            updatedJob.setCategory(jobDTO.getCategory());
            updatedJob.setLocation(jobDTO.getLocation());
            updatedJob.setJobType(jobDTO.getJobType());
            updatedJob.setExperienceLevel(jobDTO.getExperienceLevel());
            updatedJob.setSalary(jobDTO.getSalary());
            updatedJob.setDescription(jobDTO.getDescription());
            updatedJob.setRequirements(jobDTO.getRequirements());
            updatedJob.setCompanyName(jobDTO.getCompanyName());
            updatedJob.setCompanyIndustry(jobDTO.getCompanyIndustry());
            updatedJob.setCompanySize(jobDTO.getCompanySize());
            updatedJob.setSkills(jobDTO.getSkills());
            updatedJob.setEducationLevel(jobDTO.getEducationLevel());
            updatedJob.setJobTags(jobDTO.getJobTags());
            updatedJob.setBenefits(jobDTO.getBenefits());
            updatedJob.setEmploymentStatus(jobDTO.getEmploymentStatus());
            updatedJob.setApplicationType(jobDTO.getApplicationType());
            updatedJob.setJobPostingStartDate(jobDTO.getJobPostingStartDate());
            updatedJob.setJobPostingEndDate(jobDTO.getJobPostingEndDate());
            updatedJob.setRemoteWork(jobDTO.isRemoteWork());
            updatedJob.setApplyDeadline(jobDTO.getApplyDeadline());

            return Optional.of(jobRepository.save(updatedJob));

        }
       throw new JobNotFoundException("Job not found with id: " + id);

    }

public void deleteJob(String id) {
    Optional<Job>existingWork = jobRepository.findById(id);
    if (existingWork.isPresent()){
        jobRepository.deleteById(id);
    }else{
        throw new JobNotFoundException("details are not available in Database");
    }
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

