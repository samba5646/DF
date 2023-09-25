package com.geethamsoft.NearByJobs.repository;


import com.geethamsoft.NearByJobs.model.Job;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobRepository extends MongoRepository<Job, String> {
}

