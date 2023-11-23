package com.geethamsoft.NearByJobs.controller;



import com.geethamsoft.NearByJobs.dto.JobDTO;
import com.geethamsoft.NearByJobs.dto.JobSearchDTO;
import com.geethamsoft.NearByJobs.exception.ResourceNotFoundException;
import com.geethamsoft.NearByJobs.model.Job;
import com.geethamsoft.NearByJobs.service.JobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/jobs")
public class JobController {
    @Autowired
    private JobService jobService;

    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        List<Job> jobs = jobService.getAllJobs();
        return new ResponseEntity<>(jobs,HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable String id){
        Optional<Job> jobOptional =jobService.getJobById(id);
        return jobOptional.map(value ->new ResponseEntity<>(value ,HttpStatus.OK))
                .orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/search")
    public ResponseEntity<List<Job>> searchJobs(@RequestBody JobSearchDTO searchDTO) {
        List<Job> jobs = jobService.searchJobs(searchDTO);
        return ResponseEntity.ok(jobs);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addJob(@Valid @RequestBody JobDTO jobDTO) {
        Job job = jobService.addJob(jobDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(job);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable String id,@Valid  @RequestBody JobDTO jobDTO) {
       Optional<Job> optionalJob =jobService.updateJob(id,jobDTO);
       return optionalJob.map(value-> new ResponseEntity<>(value , HttpStatus.OK))
               .orElseGet(()-> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
    @DeleteMapping("/job/{id}")
    public ResponseEntity<Void> deleteWork(@PathVariable String id) {
        jobService.deleteJob(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}

