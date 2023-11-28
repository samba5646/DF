package com.geethamsoft.NearByProfessionals.repository;

import com.geethamsoft.NearByProfessionals.model.Professional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProfessionalRepository extends MongoRepository<Professional,String> {

}
