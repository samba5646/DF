package com.geethamsoft.NearByJobs.controller;



import com.geethamsoft.NearByJobs.dto.JobDTO;
import com.geethamsoft.NearByJobs.dto.JobSearchDTO;
import com.geethamsoft.NearByJobs.exception.ResourceNotFoundException;
import com.geethamsoft.NearByJobs.model.Job;
import com.geethamsoft.NearByJobs.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/jobs")
public class JobController {
    @Autowired
    private JobService jobService;

    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        List<Job> jobs = jobService.getAllJobs();
        return ResponseEntity.ok(jobs);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Job>> searchJobs(@RequestBody JobSearchDTO searchDTO) {
        List<Job> jobs = jobService.searchJobs(searchDTO);
        return ResponseEntity.ok(jobs);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addJob(@RequestBody JobDTO jobDTO) {
        Job job = jobService.addJob(jobDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(job);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateJob(@PathVariable String id, @RequestBody JobDTO jobDTO) {
        try {
            Job job = jobService.updateJob(id, jobDTO);
            return ResponseEntity.ok(job);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Resource not found with id: " + id));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable String id) {
        try {
            jobService.deleteJob(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Resource not found with id: " + id));
        }
    }

    // Define an ErrorResponse class for handling exceptions
    static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}

