package com.geethamsoft.NearByJobs.service;

import com.geethamsoft.NearByJobs.dto.JobDTO;
import com.geethamsoft.NearByJobs.exception.JobNotFoundException;
import com.geethamsoft.NearByJobs.model.Job;
import com.geethamsoft.NearByJobs.model.TimeIdentify;
import com.geethamsoft.NearByJobs.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;
    private final TimeIdentify timeIdentify;

    public List<JobDTO> getAllJobs() {
        List<Job> jobs = jobRepository.findAll();
        return jobs.stream()
                .map(this::buildJobDTO)
                .collect(Collectors.toList());
    }

    public JobDTO getJobById(String id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new JobNotFoundException("Job not found with id: " + id));
        return buildJobDTO(job);
    }

    public JobDTO addJob(JobDTO jobDTO) {
        LocalDateTime currentDateTime = LocalDateTime.now();

        Job job = Job.builder()
                .jobTitle(jobDTO.getJobTitle())
                .category(jobDTO.getCategory())
                .location(jobDTO.getLocation())
                .jobType(jobDTO.getJobType())
                .experienceLevel(jobDTO.getExperienceLevel())
                .salary(jobDTO.getSalary())
                .description(jobDTO.getDescription())
                .requirements(jobDTO.getRequirements())
                .companyName(jobDTO.getCompanyName())
                .companyIndustry(jobDTO.getCompanyIndustry())
                .companySize(jobDTO.getCompanySize())
                .skills(jobDTO.getSkills())
                .educationLevel(jobDTO.getEducationLevel())
                .jobTags(jobDTO.getJobTags())
                .benefits(jobDTO.getBenefits())
                .employmentStatus(jobDTO.getEmploymentStatus())
                .applicationType(jobDTO.getApplicationType())
                .jobPostingStartDate(jobDTO.getJobPostingStartDate())
                .jobPostingEndDate(jobDTO.getJobPostingEndDate())
                .remoteWork(jobDTO.isRemoteWork())
                .applyDeadline(jobDTO.getApplyDeadline())
                .languageRequirements(jobDTO.getLanguageRequirements())
                .createdAt(currentDateTime)
                .updatedAt(currentDateTime)
                .build();

        Job savedJob = jobRepository.save(job);

        return JobDTO.builder()
                .jobTitle(savedJob.getJobTitle())
                .category(savedJob.getCategory())
                .location(savedJob.getLocation())
                .jobType(savedJob.getJobType())
                .experienceLevel(savedJob.getExperienceLevel())
                .salary(savedJob.getSalary())
                .description(savedJob.getDescription())
                .requirements(savedJob.getRequirements())
                .companyName(savedJob.getCompanyName())
                .companyIndustry(savedJob.getCompanyIndustry())
                .companySize(savedJob.getCompanySize())
                .skills(savedJob.getSkills())
                .educationLevel(savedJob.getEducationLevel())
                .jobTags(savedJob.getJobTags())
                .benefits(savedJob.getBenefits())
                .employmentStatus(savedJob.getEmploymentStatus())
                .applicationType(savedJob.getApplicationType())
                .jobPostingStartDate(savedJob.getJobPostingStartDate())
                .jobPostingEndDate(savedJob.getJobPostingEndDate())
                .remoteWork(savedJob.isRemoteWork())
                .applyDeadline(savedJob.getApplyDeadline())
                .languageRequirements(savedJob.getLanguageRequirements())
                .createdAtFormatted(timeIdentify.formatTimeAgo(savedJob.getCreatedAt()))
                .updatedAtFormatted(timeIdentify.formatTimeAgo(savedJob.getUpdatedAt()))
                .build();
    }

    public JobDTO updateJob(String id, JobDTO jobDTO) {
        Job existingJob = jobRepository.findById(id)
                .orElseThrow(() -> new JobNotFoundException("Job not found with id: " + id));

        BeanUtils.copyProperties(jobDTO, existingJob, "id", "createdAt", "updatedAt");

        Job updatedJob = jobRepository.save(existingJob);

        return JobDTO.builder()
                .jobTitle(updatedJob.getJobTitle())
                .category(updatedJob.getCategory())
                .location(updatedJob.getLocation())
                .jobType(updatedJob.getJobType())
                .experienceLevel(updatedJob.getExperienceLevel())
                .salary(updatedJob.getSalary())
                .description(updatedJob.getDescription())
                .requirements(updatedJob.getRequirements())
                .companyName(updatedJob.getCompanyName())
                .companyIndustry(updatedJob.getCompanyIndustry())
                .companySize(updatedJob.getCompanySize())
                .skills(updatedJob.getSkills())
                .educationLevel(updatedJob.getEducationLevel())
                .jobTags(updatedJob.getJobTags())
                .benefits(updatedJob.getBenefits())
                .employmentStatus(updatedJob.getEmploymentStatus())
                .applicationType(updatedJob.getApplicationType())
                .jobPostingStartDate(updatedJob.getJobPostingStartDate())
                .jobPostingEndDate(updatedJob.getJobPostingEndDate())
                .remoteWork(updatedJob.isRemoteWork())
                .applyDeadline(updatedJob.getApplyDeadline())
                .languageRequirements(updatedJob.getLanguageRequirements())
                .createdAtFormatted(timeIdentify.formatTimeAgo(updatedJob.getCreatedAt()))
                .updatedAtFormatted(timeIdentify.formatTimeAgo(updatedJob.getUpdatedAt()))
                .build();
    }

    public String deleteJob(String id) {
        if (jobRepository.existsById(id)) {
            jobRepository.deleteById(id);
            return "Job deleted successfully.";
        } else {
            throw new JobNotFoundException("Job not found with id: " + id);
        }
    }

    private JobDTO buildJobDTO(Job job) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        String createdAtFormatted = job.getCreatedAt() != null ?
                timeIdentify.formatTimeAgo(job.getCreatedAt()) : timeIdentify.formatTimeAgo(currentDateTime);

        String updatedAtFormatted = job.getUpdatedAt() != null ?
                timeIdentify.formatTimeAgo(job.getUpdatedAt()) : timeIdentify.formatTimeAgo(currentDateTime);
        return JobDTO.builder()
                .id(job.getId())
                .jobTitle(job.getJobTitle())
                .category(job.getCategory())
                .location(job.getLocation())
                .jobType(job.getJobType())
                .experienceLevel(job.getExperienceLevel())
                .salary(job.getSalary())
                .description(job.getDescription())
                .requirements(job.getRequirements())
                .companyName(job.getCompanyName())
                .companyIndustry(job.getCompanyIndustry())
                .companySize(job.getCompanySize())
                .skills(job.getSkills())
                .educationLevel(job.getEducationLevel())
                .jobTags(job.getJobTags())
                .benefits(job.getBenefits())
                .employmentStatus(job.getEmploymentStatus())
                .applicationType(job.getApplicationType())
                .jobPostingStartDate(job.getJobPostingStartDate())
                .jobPostingEndDate(job.getJobPostingEndDate())
                .remoteWork(job.isRemoteWork())
                .applyDeadline(job.getApplyDeadline())
                .languageRequirements(job.getLanguageRequirements())
                .createdAtFormatted(createdAtFormatted)
                .updatedAtFormatted(updatedAtFormatted)
                .build();
    }
}
