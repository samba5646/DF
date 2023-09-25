package com.geethamsoft.NearByRentalService.repository;

import com.geethamsoft.NearByRentalService.model.RentalService;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalServiceRepository extends MongoRepository<RentalService, String> {
}

